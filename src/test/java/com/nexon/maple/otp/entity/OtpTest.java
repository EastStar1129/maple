package com.nexon.maple.otp.entity;

import com.nexon.maple.character.entity.CharacterInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OtpTest {

    @Test
    public void OTP_없이_생성_테스트() {
        //given
        String userName = "name";

        //when
        Otp otp = Otp.builder()
                .userName(userName)
                .build();

        //then
        assertAll(
            () -> assertEquals(userName, otp.getUserName()),
            () -> assertEquals(8, otp.getOtpNumber().length())
        );
    }

    @Test
    public void 생성_테스트() {
        //given
        String userName = "name";
        String otpNumber = "12345678";

        //when
        Otp otp = Otp.builder()
                .userName(userName)
                .otpNumber(otpNumber)
                .build();

        //then
        assertEquals(otpNumber, otp.getOtpNumber());
    }

    @Test
    public void 이름_미입력_실패테스트() {
        //given
        String otpNumber = "12345678";

        //when-then
        assertThrows(NullPointerException.class,
                () -> Otp.builder().build());
    }

    @Test
    public void OTP_8자리가아닌경우_실패테스트() {
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