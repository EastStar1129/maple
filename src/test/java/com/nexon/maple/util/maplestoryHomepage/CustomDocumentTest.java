package com.nexon.maple.util.maplestoryHomepage;

import com.nexon.maple.util.maplestoryHomepage.object.MapleCharacter;
import com.nexon.maple.util.maplestoryHomepage.object.MapleComment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CustomDocumentTest {

    @Test
    public void 댓글_테스트() throws IOException {
        //given
        String name = "구로5동혀로";
        String otpNumber = "M1770A";
        Long pageNumber = 1L;

        //when
        MapleComment mapleComment = new CustomMapleComment(name, pageNumber).build();

        //then
        assertAll(
                () -> assertTrue(mapleComment.equalsCommentContent(otpNumber)),
                () -> assertNotNull(mapleComment.commentSn()),
                () -> assertNotNull(mapleComment.parentCommentSn()),
                () -> assertNotNull(mapleComment.commentWriterName()),
                () -> assertNotNull(mapleComment.emoticonNo()),
                () -> assertNotNull(mapleComment.imageSn()),
                () -> assertNotNull(mapleComment.commentContent()),
                () -> assertNotNull(mapleComment.worldImg())
        );
    }

    @Test
    public void 존재하지않는_댓글_테스트() throws IOException {
        //given
        String name = "admin"; // admin은 닉네임 금지어이므로 절대 나올수 없다.
        String otpNumber = "M1770A";
        Long pageNumber = 1L;

        //when
        MapleComment mapleComment = new CustomMapleComment(name, pageNumber).build();

        //then
        assertNull(mapleComment);
    }

    @ParameterizedTest
    @ValueSource(strings = {"심밧드의삶", "이딴삶", "오지환"})
    public void 캐릭터_테스트(String characterName) throws IOException {

        //when
        MapleCharacter mapleCharacter = new CustomMapleCharacter(characterName).build();

        //then
        assertNotNull(mapleCharacter);
    }

    @Test
    public void 캐릭터_테스트() throws IOException {
        //given
        String characterName = "admin";
        
        //when
        MapleCharacter mapleCharacter = new CustomMapleCharacter(characterName).build();

        //then
        assertNull(mapleCharacter);
    }
}