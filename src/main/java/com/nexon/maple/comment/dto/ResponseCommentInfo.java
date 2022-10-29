package com.nexon.maple.comment.dto;

import com.nexon.maple.comment.entity.CommentInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public static List<ResponseCommentInfo> ofList(List<CommentInfo> commentInfoList) {
        List<ResponseCommentInfo> list = new ArrayList<>();
        for(CommentInfo commentInfo: commentInfoList) {
            list.add(ResponseCommentInfo.builder()
                    .id(commentInfo.getId())
                    .userId(commentInfo.getUserId())
                    .comment(commentInfo.getComment())
                    .createdAt(commentInfo.getCreatedAt())
                    .build());
        }

        return list;
    }
}