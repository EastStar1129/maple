package com.nexon.maple.terms.dto;

import com.nexon.maple.terms.entity.TermsInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ResponseTermsInfoDTO {
    private Long code;
    private String type;
    private String name;
    private String content;
    private String requiredTerms;

    @Builder
    private ResponseTermsInfoDTO(Long code, String type, String name, String content, String requiredTerms) {
        this.code = code;
        this.type = type;
        this.name = name;
        this.content = content;
        this.requiredTerms = requiredTerms;
    }

    public static ResponseTermsInfoDTO of(TermsInfo termsInfo) {
        return ResponseTermsInfoDTO.builder()
                .code(termsInfo.getCode())
                .type(termsInfo.getType())
                .name(termsInfo.getName())
                .content(termsInfo.getContent())
                .requiredTerms(termsInfo.getRequiredTerms())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseTermsInfoDTO that = (ResponseTermsInfoDTO) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
