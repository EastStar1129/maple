package com.nexon.maple.character.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterInfoTest {

    @Test
    public void 생성_테스트() {
        //given
        String name = "name";

        //when
        CharacterInfo characterInfo = CharacterInfo.builder()
                .name("name")
                .build();

        //then
        assertEquals(name, characterInfo.getName());
    }

    @Test
    public void name_없이_생성_실패테스트() {
        //when-then
        assertThrows(IllegalArgumentException.class,
                () -> CharacterInfo.builder().build());
    }
}