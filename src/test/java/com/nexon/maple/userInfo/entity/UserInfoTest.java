package com.nexon.maple.userInfo.entity;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInfoTest {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void 캐릭터명과_패스워드입력_패스워드가암호화되는지_테스트() {
        //given
        String name = "캐릭터";
        String password = "password";

        UserInfo userInfo = UserInfo.builder()
                .name(name)
                .password(password)
                .build();

        //when
        userInfo.encryptPassword(bCryptPasswordEncoder);

        /*
         * 코드는 옵션사항이다.
         * 패스워드는 암호화되어 저장된다.
         * */
        assertAll(
                () -> assertEquals(name, userInfo.getName()),
                () -> assertTrue(bCryptPasswordEncoder.matches(password, userInfo.getPassword()))
        );
    }

    @Test
    public void 이름은12자리_패스워드는8에서15자리_실패_테스트() {
        //given
        String name = "1234567890123";
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