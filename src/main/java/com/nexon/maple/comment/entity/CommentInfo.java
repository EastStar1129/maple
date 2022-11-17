package com.nexon.maple.comment.entity;

import com.nexon.maple.comment.dto.WriteCommentDTO;
import com.nexon.maple.userInfo.dto.ResponseUserInfoDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@Getter
public class CommentInfo {
    private Long id;
    private String characterName;
    private Long userId;
    private String comment;
    private LocalDateTime createdAt;

    @Builder
    public CommentInfo(Long id, String characterName, Long userId, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.characterName = Objects.requireNonNull(characterName);
        this.userId = Objects.requireNonNull(userId);
        this.comment = Objects.requireNonNull(comment);
        this.createdAt = createdAt;
    }

    public static CommentInfo of(WriteCommentDTO writeCommentDTO, ResponseUserInfoDTO userInfo) {
        CommentInfo commentInfo = CommentInfo.builder()
                .characterName(writeCommentDTO.getCharacterName())
                .userId(userInfo.id())
                .comment(writeCommentDTO.getComment())
                .createdAt(LocalDateTime.now())
                .build();
        return commentInfo;
    }
}
