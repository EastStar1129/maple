package com.nexon.maple.terms.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TermsType {
    LOGIN("로그인");

    @Getter
    private final String title;
}
