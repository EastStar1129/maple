package com.nexon.maple.login.controller;


import com.nexon.maple.login.service.LoginService;
import com.nexon.maple.userInfo.dto.RegisterUserInfoCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity register(@RequestBody RegisterUserInfoCommand command) {
        loginService.login(command);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentException(Exception ex) {
        return ResponseEntity.unprocessableEntity().build();
    }
}
