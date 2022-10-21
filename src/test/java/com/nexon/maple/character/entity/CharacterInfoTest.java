package com.nexon.maple.character.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterInfoTest {

    @Test
    public void name을입력하지않은_실패_테스트() {
        //when-then
        assertThrows(IllegalArgumentException.class,
                () -> CharacterInfo.builder().build());
    }
}