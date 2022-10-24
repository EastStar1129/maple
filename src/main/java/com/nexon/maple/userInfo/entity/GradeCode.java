package com.nexon.maple.userInfo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GradeCode {
    ROLE_ADMIN("R"),
    ROLE_USER("1"),
    ROLE_BLOCK("2");

    @Getter
    private final String title;
}
