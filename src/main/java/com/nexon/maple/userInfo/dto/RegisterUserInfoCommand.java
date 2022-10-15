package com.nexon.maple.userInfo.dto;

import java.util.List;

public record RegisterUserInfoCommand(
        String name,
        String password,
        String otpNumber,
        String pageNumber,
        List<String> terms
) {
}
