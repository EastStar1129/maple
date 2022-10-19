package com.nexon.maple.userInfo.entity;

import com.nexon.maple.util.Encryption.SHA256;
import com.nexon.maple.userInfo.dto.UserName;
import lombok.*;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserInfo {
    private Long id;
    private String password;
    private String name;
    private String gradeCode;
    private LocalDateTime createdAt;

    private static final Long PASSWORD_MIN_LENGTH = 8L;
    private static final Long PASSWORD_MAX_LENGTH = 15L;

    @Builder
    public UserInfo(Long id, String password, String name, String gradeCode, LocalDateTime createdAt) {
        this.id = id;

        validatePassword(password);
        this.name = new UserName(name).getUserName();
        this.password = new SHA256().encrypt(password);

        this.gradeCode = gradeCode == null ? GradeCode.USER.getTitle() : gradeCode;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    private void validatePassword(String password) {
        Assert.notNull(password, "패스워드가 입력되지 않았습니다.");
        Assert.isTrue(password.length() >= PASSWORD_MIN_LENGTH, "최소 길이 미만입니다.");
        Assert.isTrue(password.length() <= PASSWORD_MAX_LENGTH, "최대 길이를 초과했습니다.");
    }
}
