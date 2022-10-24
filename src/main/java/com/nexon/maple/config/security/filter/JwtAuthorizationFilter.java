package com.nexon.maple.config.security.filter;

import com.nexon.maple.config.security.jwt.JwtToken;
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

    private JwtToken jwtToken;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtToken jwtToken) {
        super(authenticationManager);
        this.jwtToken = jwtToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String accessToken = getAccessToken(request);
        String refreshToken = getRefreshToken(request);

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
                removeRefreshToken(response);
            }
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return ;
        }

        if(Objects.isNull(refreshToken)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return ;
        }

        checkAuthentication(response, accessToken, refreshToken);
    }

    private void removeRefreshToken(HttpServletResponse response) {
        Cookie cookie = new Cookie(jwtToken.getHeader(), "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private String getRefreshToken(HttpServletRequest request) {
        if(Objects.isNull(request.getCookies())) {
            return null;
        }

        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals(jwtToken.getHeader())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    private String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader(jwtToken.getHeader());
        if(Objects.isNull(accessToken)) {
            return null;
        }

        return jwtToken.typeRemove(accessToken);
    }

    private void checkAuthentication(HttpServletResponse response, String accessToken, String refreshToken) throws IOException {
        if(Objects.nonNull(response.getHeader("X-Token"))) {
            response.setHeader("X-Token", "");
            return ;
        }

        if(jwtToken.validateToken(accessToken)) {
            SecurityContextHolder.getContext().setAuthentication(jwtToken.getAuthentication(accessToken));
            return ;
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        if(!jwtToken.isExpired(accessToken)) {
            return ;
        }

        //1. accessToken 만료
        //2. refreshToken 검증완료
        if(jwtToken.validateToken(refreshToken)) {
            response.setHeader("X-Token", "true");
            return ;
        }

        // refreshToken 검증실패
        removeRefreshToken(response);
        return ;
    }
}