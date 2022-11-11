package com.nexon.maple.otp.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Getter
@NoArgsConstructor
public class Otp {
    private Long idx;
    private String userName;
    private String otpNumber;
    private LocalDateTime createdAt;
    private Integer minute = 3;

    private static final int OTP_NUMBER_LENGTH = 8;

    @Builder
    private Otp(Long idx, String userName, String otpNumber, LocalDateTime createdAt) {
        this.idx = idx;
        this.userName = userName;
        this.otpNumber = otpNumber == null ? makeOtpNumber(OTP_NUMBER_LENGTH) : otpNumber;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    private static void validateOtpNumber(String otpNumber) {
        if(Objects.isNull(otpNumber)) {
            return;
        }

        Assert.isTrue(otpNumber.length() == OTP_NUMBER_LENGTH, "OTP 번호는 8자리 입니다.");
    }

    public String makeOtpNumber(int num) {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < num; i++) {
            int index = rnd.nextInt(2);
            switch (index) {
                case 0:
                    key.append(add(rnd.nextInt(26), 65));
                    break;
                case 1:
                    key.append(rnd.nextInt(10));
                    break;
            }
        }
        return key.toString();
    }

    private char add(int a, int b) {
        return (char) (a + b);
    }

    public static Otp of(String userName) {
        return Otp.builder()
                .userName(userName)
                .build();
    }

    public static Otp of(String userName, String otpNumber) {
        validateOtpNumber(otpNumber);

        return Otp.builder()
                .userName(userName)
                .userName(otpNumber)
                .build();
    }
}
