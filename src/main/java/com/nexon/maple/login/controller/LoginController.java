package com.nexon.maple.login.controller;


import com.nexon.maple.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private final LoginService loginService;

//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody RegisterUserInfoCommand command) {
//        loginService.login(command);
//
//        return ResponseEntity.ok().build();
//    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentException(Exception ex) {
        return ResponseEntity.unprocessableEntity().build();
    }
}
