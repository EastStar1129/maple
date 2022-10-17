package com.nexon.maple.userInfo.entity;


import com.nexon.maple.comm.Encryption.SHA256;
import com.nexon.maple.otp.entity.Otp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoTest {

    @Test
    public void 생성_테스트() {
        //given
        String name = "캐릭터";
        String password = "password";

        //when
        UserInfo userInfo = UserInfo.builder()
                .name(name)
                .password(password)
                .build();

        /*
         * 코드는 옵션사항이다.
         * 패스워드는 암호화되어 저장된다.
         * */
        assertAll(
                () -> assertEquals(name, userInfo.getName()),
                () -> assertNotEquals(password, userInfo.getPassword())
        );
    }

    @Test
    public void 이름은10자리_패스워드는8에서15자리_실패_테스트() {
        //given
        String name = "12345678901";
        String password = "123456789123456789";
        String password2 = "12";

        String name_pass = "123456";
        String password_pass = "123456789";

        //when-then

        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                                () -> UserInfo.builder()
                                        .name(name_pass)
                                        .password(password)
                                        .build()),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> UserInfo.builder()
                                .name(name_pass)
                                .password(password2)
                                .build()),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> UserInfo.builder()
                                .name(name)
                                .password(password_pass)
                                .build())
        );
    }

}