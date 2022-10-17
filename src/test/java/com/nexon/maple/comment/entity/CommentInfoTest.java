package com.nexon.maple.comment.entity;

import com.nexon.maple.character.entity.CharacterInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentInfoTest {

    @Test
    public void 생성_테스트() {
        //given
        Long characterId = 1L;
        Long userId = 1L;
        String comment = "comment";

        //when
        CommentInfo commentInfo = CommentInfo.builder()
                .characterId(characterId)
                .userId(userId)
                .comment(comment)
                .build();

        //then
        assertAll(
                () -> assertEquals(characterId, commentInfo.getCharacterId()),
                () -> assertEquals(userId, commentInfo.getUserId()),
                () -> assertEquals(comment, commentInfo.getComment())
        );
    }

    @Test
    public void 필수값_없이_생성_실패테스트() {
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