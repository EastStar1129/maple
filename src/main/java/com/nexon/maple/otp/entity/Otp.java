package com.nexon.maple.otp.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private static final int otpNumberLength = 8;

    @Builder
    public Otp(Long idx, String userName, String otpNumber, LocalDateTime createdAt) {
        this.idx = idx;
        this.userName = userName;

        validateOtpNumber(otpNumber);
        this.otpNumber = otpNumber == null ? makeOtpNumber(otpNumberLength) : otpNumber;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    private void validateOtpNumber(String otpNumber) {
        if(Objects.isNull(otpNumber)) {
            return;
        }

        if(otpNumber.length() != otpNumberLength) {
            new IllegalArgumentException("OTP는 8자리 입니다.");
        }
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
}