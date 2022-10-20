package com.nexon.maple.config.security.filter;

import com.nexon.maple.config.security.auth.PrincipalDetails;
import com.nexon.maple.config.security.jwt.JwtToken;
import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        String username = getUserName(request);

        if(username != null && ! username.equals("")) {
            UserInfo userInfo = userInfoDao.findByName(username);
            PrincipalDetails principalDetails = new PrincipalDetails(userInfo);
            //Jwt 토큰 서명을 통해서 서명이 정상이면 Authenication 객체를 만들어 준다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,
                    null,
                            principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private String getUserName(HttpServletRequest request) throws IOException, ServletException {
        String jwtHeader = request.getHeader("Authorization");
        String username = jwtToken.getUserName(jwtHeader);
        return username;
    }
}