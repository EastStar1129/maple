package com.nexon.maple.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexon.maple.userInfo.dto.RegisterUserInfoDTO;
import com.nexon.maple.userInfo.dto.ResponseUserInfoDTO;
import com.nexon.maple.userInfo.service.UserInfoReadService;
import com.nexon.maple.userInfo.service.UserInfoWriteService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomBean {

    public static ResponseUserInfoDTO 회원가입(UserInfoWriteService userInfoWriteService, UserInfoReadService userInfoReadService, RegisterUserInfoDTO registerUserInfoDTO) {
        var userInfo = userInfoReadService.selectUserInfo(registerUserInfoDTO.getName());
        return Objects.isNull(userInfo) ? ResponseUserInfoDTO.of(userInfoWriteService.regist(registerUserInfoDTO)) : userInfo;
    }

    public static RegisterUserInfoDTO ofRegisterUserInfoDTO(String name, String password) {
        Map input = new HashMap();
        input.put("name", name);
        input.put("password", password);
        return new ObjectMapper().convertValue(input, RegisterUserInfoDTO.class);
    }

    public static RegisterUserInfoDTO ofRegisterUserInfoDTO(String name, String password, String otpNumber, Long pageNumber, List terms) {
        Map input = new HashMap();
        input.put("name", name);
        input.put("password", password);
        input.put("otpNumber", otpNumber);
        input.put("pageNumber", pageNumber);
        if(!Objects.isNull(terms)) {
            input.put("terms", terms);
        }
        return new ObjectMapper().convertValue(input, RegisterUserInfoDTO.class);
    }
}
