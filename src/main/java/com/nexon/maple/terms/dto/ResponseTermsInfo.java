package com.nexon.maple.terms.dto;

import com.nexon.maple.terms.entity.TermsInfo;
import lombok.Getter;

@Getter
public class ResponseTermsInfo {
    Long code;
    String type;
    String name;
    String content;
    String requiredTerms;

    public ResponseTermsInfo(TermsInfo termsInfo) {
        this.code = termsInfo.getCode();
        this.type = termsInfo.getType();
        this.name = termsInfo.getName();
        this.content = termsInfo.getContent();
        this.requiredTerms = termsInfo.getRequiredTerms();
    }
}
