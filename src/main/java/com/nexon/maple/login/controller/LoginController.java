package com.nexon.maple.login.controller;


import com.nexon.maple.config.security.auth.PrincipalDetails;
import com.nexon.maple.config.security.jwt.JwtToken;
import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private final JwtToken jwtToken;
    private final UserInfoDao userInfoDao;

    @GetMapping("/effectiveToken")
    public ResponseEntity effectiveToken(HttpServletResponse response) {
        return ResponseEntity.status(response.getStatus()).build();
    }

    @GetMapping("/reissue")
    public ResponseEntity reissue(HttpServletResponse response, HttpServletRequest request) {
        if(response.getHeader("x-token").equals("true")) {
            String accessToken = jwtToken.typeRemove(request.getHeader(jwtToken.getHeader()));
            String userName = jwtToken.getUserName(accessToken);

            UserInfo userInfo = userInfoDao.findByName(userName);
            response.addHeader(jwtToken.getHeader(),
                    jwtToken.getType()+ " " + jwtToken.generateAccessToken(new PrincipalDetails(userInfo)));

            Cookie RefreshTokenCookie = new Cookie(jwtToken.getHeader(), jwtToken.generateRefreshToken());
            RefreshTokenCookie.setMaxAge(jwtToken.REFRESH_TOKEN_EXPIRE_TIME);
            RefreshTokenCookie.setHttpOnly(true);
            RefreshTokenCookie.setSecure(true);
            response.addCookie(RefreshTokenCookie);
        }
        return ResponseEntity.status(response.getStatus()).build();
    }
}
