package com.nexon.maple.otp.controller;

import com.nexon.maple.otp.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OtpController {
    private final OtpService otpService;

    @PostMapping("/{name}/otps")
    public ResponseEntity<String> createOtp(@PathVariable("name") String userName) {
        return ResponseEntity.ok().body(otpService.createOtp(userName));
    }

}
