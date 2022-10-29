package com.nexon.maple.config.security.filter;

import com.nexon.maple.config.security.jwt.JwtToken;
import com.nexon.maple.login.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private JwtToken jwtToken;
    private final LoginService loginService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtToken jwtToken, LoginService loginService) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.jwtToken = jwtToken;
        this.loginService = loginService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String accessToken = getToken(request, jwtToken.getAccessTokenName());
        String refreshToken = getToken(request, jwtToken.getRefreshTokenName());

        authentication(response, accessToken, refreshToken);
        chain.doFilter(request, response);
    }

    /*
        ** AccessToken 존재하는 경우에만 로직 **
        1. Refresh Token 존재 : 검증
        2. Refresh Token 없음 : AccessToken 탈취 ( 로그아웃 )

        없는 경우 = localStorage 값이 없음 = 로그아웃, Refresh 토큰 탈취
     */
    private void authentication(HttpServletResponse response, String accessToken, String refreshToken)
            throws IOException {

        if(Objects.isNull(accessToken)) {
            if(Objects.nonNull(refreshToken)) {
                removeToken(response);
            }
            return ;
        }

        if(Objects.isNull(refreshToken)) {
            removeToken(response);
            return ;
        }

        checkAuthentication(response, accessToken, refreshToken);
    }

    private void removeToken(HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Cookie cookie = new Cookie(jwtToken.getAccessTokenName(), "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        Cookie cookie2 = new Cookie(jwtToken.getRefreshTokenName(), "");
        cookie2.setMaxAge(0);
        response.addCookie(cookie2);
        Cookie cookie3 = new Cookie(jwtToken.getTokenFlagName(), "");
        cookie3.setMaxAge(0);
        response.addCookie(cookie3);
    }

    private String getToken(HttpServletRequest request, String tokeName) {
        if(Objects.isNull(request.getCookies())) {
            return null;
        }

        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals(tokeName)) {
                return cookie.getValue();
            }
        }

        return null;
    }

    private void checkAuthentication(HttpServletResponse response, String accessToken, String refreshToken) throws IOException {
        if(jwtToken.validateToken(accessToken)) {
            SecurityContextHolder.getContext().setAuthentication(jwtToken.getAuthentication(accessToken));
            return ;
        }
        if(!jwtToken.isExpired(accessToken)) {
            removeToken(response);
            return ;
        }

        //1. accessToken 만료
        //2. refreshToken 검증실패
        if(!jwtToken.validateToken(refreshToken)) {
            removeToken(response);
            return ;
        }

        //2. refreshToken 검증완료
        // >>> 재발급
        String userName = jwtToken.getUserName(accessToken);
        loginService.addHeaderToken(response, userName);
    }
}