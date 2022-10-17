package com.nexon.maple.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@Getter
public class ResponseCommentInfo {
    private Long id;
    private Long userId;
    private String comment;
    private LocalDateTime createdAt;

    @Builder
    public ResponseCommentInfo(Long id, Long userId, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId);
        this.comment = Objects.requireNonNull(comment);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }
}