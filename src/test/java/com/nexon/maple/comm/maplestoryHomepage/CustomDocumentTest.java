package com.nexon.maple.comm.maplestoryHomepage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomDocumentTest {

    @Test
    public void 댓글_테스트() {
        //given
        String name = "구로5동혀로";
        String otpNumber = "M1770A";
        String pageNumber = "1";

        //when
        CustomMapleComment customComment = new CustomMapleComment(name, pageNumber);

        //then
        assertTrue(customComment.equalsComment(otpNumber));
    }

    @Test
    public void 존재하지않는_댓글_테스트() {
        //given
        String name = "admin"; // admin은 닉네임 금지어이므로 절대 나올수 없다.
        String otpNumber = "M1770A";
        String pageNumber = "1";

        //when
        CustomMapleComment customComment = new CustomMapleComment(name, pageNumber);

        //then
        assertFalse(customComment.equalsComment(otpNumber));
    }

    @Test
    public void 캐릭터_테스트() {
        //given
        String admin = "admin";
        String user = "구로5동혀로";

        //when
        CustomMapleCharacter customMapleCharacter = new CustomMapleCharacter(admin);
        CustomMapleCharacter customMapleCharacter2 = new CustomMapleCharacter(user);

        //then
        assertNull(customMapleCharacter.getMapleCharacter());
        assertNotNull(customMapleCharacter2.getMapleCharacter());
    }
}