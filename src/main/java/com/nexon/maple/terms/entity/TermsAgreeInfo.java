package com.nexon.maple.terms.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

//@NoArgsConstructor
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
        this.userId = userId;
        this.termsCode = termsCode;
        this.agreeYn = agreeYn;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public static TermsAgreeInfo ofAgree(Long terms, Long userId) {
        return TermsAgreeInfo.builder()
                .userId(Objects.requireNonNull(userId))
                .termsCode(Objects.requireNonNull(terms))
                .agreeYn("Y")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static TermsAgreeInfo ofCancel(Long idx) {
        return TermsAgreeInfo.builder()
                .idx(idx)
                .deletedAt(LocalDateTime.now())
                .build();
    }
}
