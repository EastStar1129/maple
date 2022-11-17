package com.nexon.maple.otp.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}