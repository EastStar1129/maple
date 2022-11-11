package com.nexon.maple.userInfo.dto;

import com.nexon.maple.userInfo.entity.UserInfo;

public record ResponseUserInfoDTO(
        Long id,
        String name,
        String gradeCode
){
    public static ResponseUserInfoDTO of(UserInfo userInfo) {
        return new ResponseUserInfoDTO(userInfo.getId(), userInfo.getName(), userInfo.getGradeCode());
    }
}
