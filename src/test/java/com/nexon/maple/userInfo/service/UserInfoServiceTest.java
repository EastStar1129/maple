package com.nexon.maple.userInfo.service;

import com.nexon.maple.userInfo.dto.RegisterUserInfoDTO;
import com.nexon.maple.userInfo.dto.ResponseUserInfoDTO;
import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.util.CustomBean;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMybatis
class UserInfoServiceTest {

    @Autowired
    private UserInfoReadService userInfoReadService;

    @Autowired
    private UserInfoWriteService userInfoWriteService;

    @Test
    @Transactional
    public void 회원가입() {
        //given
        RegisterUserInfoDTO registerUserInfoDTO = CustomBean.ofRegisterUserInfoDTO("GM", "12341234");

        //when
        UserInfo userInfo = 회원가입(registerUserInfoDTO);

        //then
        assertNotNull(userInfo.getId());
    }

    @Test
    @Transactional
    public void 회원가입_확인() {
        //given
        RegisterUserInfoDTO registerUserInfoDTO = CustomBean.ofRegisterUserInfoDTO("GM", "12341234");

        //when
        회원가입(registerUserInfoDTO);
        ResponseUserInfoDTO responseUserInfoDTO = 회원조회(registerUserInfoDTO.getName());

        //then
        assertNotNull(responseUserInfoDTO);
    }

    private UserInfo 회원가입(RegisterUserInfoDTO registerUserInfoDTO) {
        return userInfoWriteService.regist(registerUserInfoDTO);
    }

    private ResponseUserInfoDTO 회원조회(String userName) {
        return userInfoReadService.selectUserInfo(userName);
    }

}