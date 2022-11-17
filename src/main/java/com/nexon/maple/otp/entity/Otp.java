package com.nexon.maple.otp.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Random;

@Getter
@NoArgsConstructor
public class Otp {
    private Long idx;
    private String userName;
    private String otpNumber;
    private LocalDateTime createdAt;
    private Random rnd = new Random();

    private static final int OTP_NUMBER_LENGTH = 8;

    @Builder
    private Otp(Long idx, String userName, String otpNumber, LocalDateTime createdAt) {
        this.idx = idx;
        this.userName = userName;
        this.otpNumber = otpNumber == null ? makeOtpNumber(OTP_NUMBER_LENGTH) : otpNumber;
        this.createdAt = createdAt;
    }

    private String makeOtpNumber(int num) {
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < num; i++) {
            if(randomFlag()) {
                key.append(add(rnd.nextInt(26), 65));
                continue;
            }
            key.append(rnd.nextInt(10));
        }
        return key.toString();
    }

    private boolean randomFlag() {
        return rnd.nextInt(2) == 0;
    }

    private char add(int a, int b) {
        return (char) (a + b);
    }

    public static Otp of(String userName) {
        return Otp.builder()
                .userName(userName)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Otp of(String userName, String otpNumber) {
        return Otp.builder()
                .userName(userName)
                .otpNumber(otpNumber)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
