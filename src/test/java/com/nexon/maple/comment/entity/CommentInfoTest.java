package com.nexon.maple.comment.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommentInfoTest {

    @Test
    public void 필수값인_캐릭터번호_유저번호_댓글을입력하지않은_실패테스트() {
        //given
        Long characterId = 1L;
        Long userId = 1L;
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
                                .characterId(characterId)
                                .comment(comment)
                                .build()),
                () -> assertThrows(NullPointerException.class,
                        () -> CommentInfo.builder()
                                .characterId(characterId)
                                .userId(userId)
                                .build())
        );
    }
}