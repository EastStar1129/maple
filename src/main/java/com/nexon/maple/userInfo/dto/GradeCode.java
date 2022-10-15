package com.nexon.maple.userInfo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GradeCode {
    ADMIN("00"),
    USER("01"),
    BLOCK("02");

    @Getter
    private final String title;
}
