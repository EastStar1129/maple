package com.nexon.maple.login.service;

import com.nexon.maple.userInfo.dto.RegisterUserInfoCommand;
import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import com.nexon.maple.userInfo.service.UserInfoReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserInfoReadService userInfoReadService;

    public void login(RegisterUserInfoCommand command) {
        UserInfo userInfo = UserInfo.builder()
                .name(command.name())
                .password(command.password())
                .build();

        validateUserInfo(userInfo);
        /*
            TODO : jwt vs Spring session
            1. jwt : oauth2까지 적용 및 확장성까지 고려하면 jwt를 구현하면 좋다.
            하지만 언제 할 지 모르겠다.
            2. Spring session

        */

    }

    private void validateUserInfo(UserInfo userInfo) {
        Assert.notNull(userInfoReadService.selectUserInfo(userInfo), "사용자가 존재하지 않습니다.");
    }
}
