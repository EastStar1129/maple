package com.nexon.maple.login.controller;


import com.nexon.maple.config.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@Validated
public class LoginController {

    @GetMapping("/effectiveToken")
    public ResponseEntity effectiveToken(Principal principal, HttpServletResponse response) {
        if(principal != null){
            return ResponseEntity.status(response.getStatus()).body(ResponseDTO.ofSuccess("로그인", principal.getName()));
        }

        return ResponseEntity.status(response.getStatus()).body(ResponseDTO.ofSuccess());
    }
}
