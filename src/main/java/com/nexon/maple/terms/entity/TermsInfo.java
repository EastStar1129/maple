package com.nexon.maple.terms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TermsInfo {
    Long code;
    String type;
    String name;
    String content;
    String requiredTerms;
}
