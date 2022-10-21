package com.nexon.maple.otp.entity;

import com.nexon.maple.character.entity.CharacterInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OtpTest {

    @Test
    public void OTP번호미입력시_OTP번호가생성되는_테스트() {
        //given
        String userName = "name";

        //when
        Otp otp = Otp.builder()
                .userName(userName)
                .build();

        //then
        assertAll(
            () -> assertEquals(userName, otp.getUserName()),
            () -> assertEquals(Otp.OTP_NUMBER_LENGTH, otp.getOtpNumber().length())
        );
    }

    @Test
    public void 이름을입력하지않으면_실패_테스트() {
        //given
        String otpNumber = "12345678";

        //when-then
        assertThrows(NullPointerException.class,
                () -> Otp.builder().build());
    }

    @Test
    public void OTP번호의길이가8자리가아닌경우_실패_테스트() {
        //given
        String userName = "name";
        String otpNumber = "1234567";

        //when-then
        assertThrows(IllegalArgumentException.class,
                () -> Otp.builder()
                        .userName(userName)
                        .otpNumber(otpNumber)
                        .build());
    }
}