package com.nexon.maple.login.controller;


import com.nexon.maple.config.security.jwt.JwtToken;
import com.nexon.maple.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private final LoginService loginService;
    private final JwtToken jwtToken;

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals(jwtToken.getHeader())) {
                cookie.setMaxAge(0);
                cookie.setPath("/"); // 모든 경로에서 삭제 됬음을 알린다.
                response.addCookie(cookie);
            }
        }

        loginService.logout();
        response.sendRedirect("/");
    }
}
