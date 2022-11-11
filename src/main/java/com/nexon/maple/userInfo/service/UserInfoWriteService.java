package com.nexon.maple.userInfo.service;

import com.nexon.maple.userInfo.dto.RegisterUserInfoDTO;
import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class UserInfoWriteService {
    private final UserInfoDao userInfoDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserInfo regist(RegisterUserInfoDTO registerUserInfoDTO){
        UserInfo userInfo = UserInfo.of(registerUserInfoDTO.getPassword(), registerUserInfoDTO.getName(), bCryptPasswordEncoder);
        userInfoDao.save(userInfo);
        Assert.notNull(userInfo.getId(), "회원가입이 되지 않았습니다.");
        return userInfo;
    }
}
