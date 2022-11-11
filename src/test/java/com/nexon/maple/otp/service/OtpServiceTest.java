package com.nexon.maple.otp.service;

import com.nexon.maple.userInfo.entity.UserName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureMybatis
class OtpServiceTest {

    @Autowired
    private OtpWriteService otpWriteService;

    @Autowired
    private OtpReadService otpReadService;

    @Test
    @Transactional
    public void OTP발급() {
        //given
        String userName = UserName.of("구로").getUserName();

        //when
        String otpNumber = OTP발급(userName);

        //then
        OTP_8자리(otpNumber);
    }

    @Test
    @Transactional
    public void 발급된_최근OTP_조회() {
        //given
        String userName = UserName.of("구로").getUserName();

        //when
        OTP발급(userName);
        String otpNumber = OTP발급(userName);
        String otpNumberSelect = OTP조회(userName);

        //then
        발급된_OTP비교(otpNumber, otpNumberSelect);
    }

    @Test
    @Transactional
    public void 발급되지않은_OTP_조회() {
        //given
        String userName = UserName.of("GM").getUserName();

        //when
        String otpNumberSelect = otpReadService.selectOtpNumber(userName);

        //then
        assertNull(otpNumberSelect);
    }

    private String OTP발급(String userName) {
        return otpWriteService.createOtp(userName);
    }

    private String OTP조회(String userName) {
        return otpReadService.selectOtpNumber(userName);
    }

    private void OTP_8자리(String otpNumber) {
        assertEquals(otpNumber.length(), 8);
    }

    private void 발급된_OTP비교(String otpNumber, String otpNumberSelect) {
        assertEquals(otpNumber, otpNumberSelect);
    }
}