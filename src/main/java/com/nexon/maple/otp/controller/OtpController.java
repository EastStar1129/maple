package com.nexon.maple.otp.controller;

import com.nexon.maple.otp.service.OtpWriteService;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.*;

@RequiredArgsConstructor
@RestController
public class OtpController {
    private final OtpWriteService otpWriteService;

    @PostMapping("/{name}/otps")
    public ResponseEntity<String> createOtp(@PathVariable("name") String userName) {
        return ResponseEntity.ok().body(otpWriteService.createOtp(userName));
    }

}
