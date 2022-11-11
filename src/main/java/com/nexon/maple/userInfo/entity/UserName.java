package com.nexon.maple.userInfo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor
public class UserName {
    private String userName;

    private static final Long NAME_MIN_LENGTH = 2L;
    private static final Long NAME_MAX_LENGTH = 12L;

    @Builder
    private UserName(String userName) {
        validate(userName);
        this.userName = userName;
    }

    private void validate(String userName) {
        Assert.notNull(userName, "이름이 입력되지 않았습니다.");
        Assert.isTrue(userName.length() >= NAME_MIN_LENGTH, "최소 길이 미만입니다.");
        Assert.isTrue(userName.length() <= NAME_MAX_LENGTH, "최대 길이를 초과했습니다.");
    }

    public static UserName of(String userName) {
        return UserName.builder()
                .userName(userName)
                .build();
    }
}
