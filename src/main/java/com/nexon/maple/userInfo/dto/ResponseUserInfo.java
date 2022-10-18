package com.nexon.maple.userInfo.dto;

import lombok.NoArgsConstructor;

public record ResponseUserInfo (
        Long id,
        String name,
        String gradeCode
){}
