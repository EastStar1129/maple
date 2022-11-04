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
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final JwtToken jwtToken;
    private final UserInfoDao userInfoDao;

    //로그인 시 사용되는 함수
    //로그인 이후 반환값 설정
    public void addHeaderToken(HttpServletResponse response, PrincipalDetails principalDetails) throws IOException {
        setHeaderToken(response, principalDetails);
        response
                .getOutputStream()
                .write(new ObjectMapper()
                        .writeValueAsString(ResponseDTO.ofSuccess())
                        .getBytes(StandardCharsets.UTF_8)
                );
    }

    //재발급 시 사용되는 함수
    public void addHeaderToken(HttpServletResponse response, String userName) throws IOException {
        UserInfo userInfo = userInfoDao.findByName(userName);
        setHeaderToken(response, new PrincipalDetails(userInfo));
    }

    //Cookie에 Token을 발급하는 공통 함수
    private void setHeaderToken(HttpServletResponse response, PrincipalDetails principalDetails) throws IOException {
        String accessToken = jwtToken.generateAccessToken(principalDetails);
        Cookie accessTokenCookie = new Cookie(jwtToken.getAccessTokenName(), accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie(jwtToken.getRefreshTokenName(), jwtToken.generateRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        response.addCookie(refreshTokenCookie);

        Cookie flagCookie = new Cookie(jwtToken.getTokenFlagName(), accessToken.split("\\.")[1]);
        response.addCookie(flagCookie);
    }
}
