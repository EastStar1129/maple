package com.nexon.maple.login.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class LoginController {

    @GetMapping("/effectiveToken")
    public ResponseEntity effectiveToken(HttpServletResponse response) {
        return ResponseEntity.status(response.getStatus()).build();
    }
}
