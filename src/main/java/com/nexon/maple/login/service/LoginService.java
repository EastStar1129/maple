package com.nexon.maple.login.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexon.maple.config.dto.ResponseDTO;
import com.nexon.maple.config.security.auth.PrincipalDetails;
import com.nexon.maple.config.security.jwt.JwtToken;
import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final JwtToken jwtToken;
    private final UserInfoDao userInfoDao;

    /*
        TODO :
         1. response 토큰을 가져옴
         2. AccessToken 검증
            2-1 검증완료 로그인
            2-2 만료 >> 3
         3. RefreshToken 검증
            3-1 검증완료 로그인 ( AccessToken 재발급, RefreshToken 재발급 )
            3-2 만료 >> 4
         4. 재로그인
     */

    public void addHeaderToken(HttpServletResponse response, PrincipalDetails principalDetails) throws IOException {
        String accessToken = jwtToken.generateAccessToken(principalDetails);
        Cookie accessTokenCookie = new Cookie(jwtToken.getAccessTokenName(), accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie(jwtToken.getRefreshTokenName(), jwtToken.generateRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        response.addCookie(refreshTokenCookie);

        Cookie flagCookie = new Cookie(jwtToken.getTokenFlagName(), jwtToken.getExpiration(accessToken));
        response.addCookie(flagCookie);

        response.getWriter().write(new ObjectMapper().writeValueAsString(ResponseDTO.ofSuccess()));
    }

    public void addHeaderToken(HttpServletResponse response, String userName) throws IOException {
        UserInfo userInfo = userInfoDao.findByName(userName);
        addHeaderToken(response, new PrincipalDetails(userInfo));
    }
}
