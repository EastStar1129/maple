package com.nexon.maple.comment.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommentInfoTest {

    @Test
    public void 필수값인_캐릭터번호_유저번호_댓글을입력하지않은_실패테스트() {
        //given
        Long userId = 1L;
        String characterName = "구로5동호영";
        String comment = "comment";

        //when-then
        assertAll(
                () -> assertThrows(NullPointerException.class,
                        () -> CommentInfo.builder()
                                .userId(userId)
                                .comment(comment)
                                .build()),
                () -> assertThrows(NullPointerException.class,
                        () -> CommentInfo.builder()
                                .characterName(characterName)
                                .comment(comment)
                                .build()),
                () -> assertThrows(NullPointerException.class,
                        () -> CommentInfo.builder()
                                .characterName(characterName)
                                .userId(userId)
                                .build())
        );
    }
}