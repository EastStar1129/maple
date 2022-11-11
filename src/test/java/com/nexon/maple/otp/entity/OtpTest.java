package com.nexon.maple.otp.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OtpTest {

    @Test
    public void OTP_8자리_랜덤생성() {
        //given
        String userName = "name";

        //when
        Otp otp = Otp.of(userName);

        //then
        assertAll(
            () -> assertEquals(userName, otp.getUserName()),
            () -> assertEquals(8, otp.getOtpNumber().length())
        );
    }

    @Test
    public void OTP_8자리_테스트() {
        //given
        String userName = "name";
        String otpNumber = "1234567";

        //when-then
        assertThrows(IllegalArgumentException.class, () -> Otp.of(userName, otpNumber));
    }
}