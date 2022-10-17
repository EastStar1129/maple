package com.nexon.maple.userInfo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GradeCode {
    ADMIN("0"),
    USER("1"),
    BLOCK("2");

    @Getter
    private final String title;
}
