package com.nexon.maple.userInfo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexon.maple.userInfo.dto.RegisterUserInfoDTO;
import com.nexon.maple.userInfo.dto.ResponseUserInfoDTO;
import com.nexon.maple.userInfo.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

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
        RegisterUserInfoDTO registerUserInfoDTO = ofRegisterUserInfoDTO("GM", "12341234");

        //when
        UserInfo userInfo = 회원가입(registerUserInfoDTO);

        //then
        assertNotNull(userInfo.getId());
    }

    @Test
    @Transactional
    public void 회원가입_확인() {
        //given
        RegisterUserInfoDTO registerUserInfoDTO = ofRegisterUserInfoDTO("GM", "12341234");

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

    public static RegisterUserInfoDTO ofRegisterUserInfoDTO(String name, String password) {
        Map input = new HashMap();
        input.put("name", name);
        input.put("password", password);
        return new ObjectMapper().convertValue(input, RegisterUserInfoDTO.class);
    }

}