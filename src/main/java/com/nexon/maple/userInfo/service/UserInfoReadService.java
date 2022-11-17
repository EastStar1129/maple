package com.nexon.maple.userInfo.service;

import com.nexon.maple.userInfo.dto.ResponseUserInfoDTO;
import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserInfoReadService {
    private final UserInfoDao userInfoDao;

    public ResponseUserInfoDTO selectUserInfo(String name) {
        UserInfo userInfo = userInfoDao.findByName(name);
        return Objects.isNull(userInfo)? null : ResponseUserInfoDTO.of(userInfo);
    }

    //중복아이디 체크
    public void validationDuplicate(String name) {
        UserInfo userInfo = userInfoDao.findByName(name);
        Assert.isNull(userInfo, "중복된 아이디가 존재합니다.");
    }
}
