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
    private Long characterId;
    private Long userId;
    private String comment;
    private LocalDateTime createdAt;

    @Builder
    public CommentInfo(Long id, Long characterId, Long userId, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.characterId = Objects.requireNonNull(characterId);
        this.userId = Objects.requireNonNull(userId);
        this.comment = Objects.requireNonNull(comment);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public static CommentInfo of(WriteCommentDTO writeCommentDTO, ResponseUserInfoDTO userInfo) {
        CommentInfo commentInfo = CommentInfo.builder()
                .characterId(writeCommentDTO.getCharacterId())
                .userId(userInfo.id())
                .comment(writeCommentDTO.getComment())
                .build();
        return commentInfo;
    }
}
