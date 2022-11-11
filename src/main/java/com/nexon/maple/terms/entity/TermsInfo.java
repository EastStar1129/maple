package com.nexon.maple.terms.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TermsInfo {
    private Long code;
    private String type;
    private String name;
    private String content;
    private String requiredTerms;

    @Builder
    public TermsInfo(Long code, String type, String name, String content, String requiredTerms) {
        this.code = code;
        this.type = type;
        this.name = name;
        this.content = content;
        this.requiredTerms = requiredTerms;
    }

    public static TermsInfo ofRequiredTerms(String type, String name, String content) {
        return TermsInfo.builder()
                .type(type)
                .name(name)
                .content(content)
                .requiredTerms("Y")
                .build();
    }
}
