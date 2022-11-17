package com.nexon.maple.userInfo.service;

import com.nexon.maple.otp.entity.Otp;
import com.nexon.maple.otp.service.OtpWriteService;
import com.nexon.maple.userInfo.dto.RegisterUserInfoDTO;
import com.nexon.maple.util.CustomBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@AutoConfigureMybatis
class RegisteUserInfoServiceTest {
    private static final String userName = "몽환의삶";

    @Autowired
    private RegisteUserInfoService registeUserInfoService;

    @Autowired
    private OtpWriteService otpWriteService;

    @BeforeEach
    @Transactional
    public void setup() {
        //given
        String otpNumber = "123456";
        Otp otp = Otp.of(userName, otpNumber);

        //when
        otpWriteService.saveOtp(otp);
    }

    @Test
    @Transactional
    public void 회원가입() {
        //given
        String password = "12341234";
        String otpNumber = "123456";
        Long pageNumber = 1L;
        List terms = List.of("1");

        RegisterUserInfoDTO registerUserInfoDTO = CustomBean.ofRegisterUserInfoDTO(userName, password, otpNumber, pageNumber, terms);

        //when-then
        registeUserInfoService.regist(registerUserInfoDTO);
    }

    @Test
    @Transactional
    public void 회원가입_필수약관미입력시_실패() {
        //given
        String password = "12341234";
        String otpNumber = "123456";
        Long pageNumber = 1L;

        RegisterUserInfoDTO registerUserInfoDTO = CustomBean.ofRegisterUserInfoDTO(userName, password, otpNumber, pageNumber, null);

        //when-then
        Assertions.assertThrows(IllegalArgumentException.class, () -> registeUserInfoService.regist(registerUserInfoDTO));
    }
}