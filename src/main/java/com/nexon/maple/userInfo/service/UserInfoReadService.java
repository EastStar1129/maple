package com.nexon.maple.userInfo.service;

import com.nexon.maple.userInfo.dto.ResponseUserInfoDTO;
import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserInfoReadService {
    private final UserInfoDao userInfoDao;

    public ResponseUserInfoDTO selectUserInfo(String name) {
        UserInfo userInfo = userInfoDao.findByName(name);
        return Objects.isNull(userInfo)? null : ResponseUserInfoDTO.of(userInfo);
    }
}
