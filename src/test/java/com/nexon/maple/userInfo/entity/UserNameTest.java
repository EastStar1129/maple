package com.nexon.maple.userInfo.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UserNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"1234123412341234", "1"})
    public void 이름에_검증_테스트(String userName) {
        //when-then
        Assertions.assertThrows(IllegalArgumentException.class, () -> UserName.builder().userName(userName).build());
    }

    @Test
    public void 이름값이_입력되지않은_테스트() {
        //when-then
        Assertions.assertThrows(IllegalArgumentException.class, () -> UserName.builder().userName(null).build());
    }
}