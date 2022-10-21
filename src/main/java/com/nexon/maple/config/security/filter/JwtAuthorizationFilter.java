package com.nexon.maple.config.security.filter;

import com.nexon.maple.config.security.auth.PrincipalDetails;
import com.nexon.maple.config.security.jwt.JwtToken;
import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserInfoDao userInfoDao;
    private JwtToken jwtToken;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserInfoDao userInfoDao, JwtToken jwtToken) {
        super(authenticationManager);
        this.userInfoDao = userInfoDao;
        this.jwtToken = jwtToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String accessToken = request.getHeader(jwtToken.getHeader());
        String refreshToken = null;
        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals(jwtToken.getHeader())) {
                refreshToken = cookie.getValue();
            }
        }

        if(Objects.nonNull(accessToken) || Objects.nonNull(refreshToken)) {
            Authentication authentication = getAuthentication(response, accessToken, refreshToken);

            if(Objects.nonNull(authentication)) {
                this.getAuthenticationManager().authenticate(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(HttpServletResponse response, String accessToken, String refreshToken) throws IOException {
        if(!jwtToken.validateToken(refreshToken)) {
            if(!jwtToken.isExpired(refreshToken)) {
                response.sendRedirect("/logout");
                return null;
            }
        }

        if(!jwtToken.validateToken(accessToken)) {
            if(!jwtToken.isExpired(accessToken)) {
                response.sendRedirect("/logout");
                return null;
            }
            String userName = jwtToken.getUserName(accessToken);
            UserInfo userInfo = userInfoDao.findByName(userName);

            PrincipalDetails principalDetails = new PrincipalDetails(userInfo);

            response.addHeader(jwtToken.getHeader(),
                    jwtToken.getType()+ " " + jwtToken.generateAccessToken(principalDetails));

            Cookie RefreshTokenCookie =
                    new Cookie(jwtToken.getHeader(), jwtToken.getType()+ " " + jwtToken.generateRefreshToken());
            RefreshTokenCookie.setMaxAge(jwtToken.REFRESH_TOKEN_EXPIRE_TIME);
            RefreshTokenCookie.setHttpOnly(true);
            RefreshTokenCookie.setSecure(true);
        }
        return jwtToken.getAuthentication(accessToken);
    }

}