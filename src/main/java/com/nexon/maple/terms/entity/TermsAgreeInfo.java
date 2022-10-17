package com.nexon.maple.terms.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@Getter
public class TermsAgreeInfo {
    private Long idx;
    private Long userId;
    private Long termsCode;
    private String agreeYn;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Builder
    public TermsAgreeInfo(Long idx, Long userId, Long termsCode, String agreeYn, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.idx = idx;
        this.userId = Objects.requireNonNull(userId);
        this.termsCode = Objects.requireNonNull(termsCode);
        this.agreeYn = agreeYn;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
        this.deletedAt = deletedAt;
    }
}
