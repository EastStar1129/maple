package com.nexon.maple.userInfo.controller;

import com.nexon.maple.userInfo.dto.RegisterUserInfoCommand;
import com.nexon.maple.userInfo.dto.ResponseUserInfo;
import com.nexon.maple.userInfo.service.RegisteUserInfoService;
import com.nexon.maple.userInfo.service.UserInfoReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final RegisteUserInfoService registeUserInfoService;
    private final UserInfoReadService userInfoReadService;

    @PostMapping("/users")
    public ResponseEntity register(@RequestBody RegisterUserInfoCommand command) {
        registeUserInfoService.regist(command);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseUserInfo> selectUserInfo(@PathVariable Long id) {
        return ResponseEntity.ok().body(userInfoReadService.selectUserInfo(id));
    }
}
