package com.nexon.maple.login.service;

import com.nexon.maple.config.security.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final JwtToken jwtToken;

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
    @Transactional(rollbackFor = Exception.class)
    public void login(String accessToken, String refreshToken) {

        return ;
    }

    /*
        redis token 삭제
        1. accessToken
        2. refreshToken
     */
    @Transactional(rollbackFor = Exception.class)
    public void logout() {
        return ;
    }

    /*
        redis token 재발급
        1. accessToken
        2. refreshToken
     */
    @Transactional(rollbackFor = Exception.class)
    public void reissue() {
        return ;
    }

    public boolean verificationRefreshToken(String refreshToken) {
        if(jwtToken.validateToken(refreshToken)) {
            return false;
        }

        if(jwtToken.getExpiration(refreshToken) <= 0) {
            return false;
        }

        return true;
    }

    public boolean verificationAccessToken(String accessToken) {
        if(jwtToken.validateToken(accessToken)) {
            return false;
        }

        if(jwtToken.getExpiration(accessToken) <= 0) {
            return false;
        }

        return true;
    }
}
