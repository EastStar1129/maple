package com.nexon.maple.userInfo.service;

import com.nexon.maple.userInfo.dto.ResponseUserInfo;
import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class UserInfoReadService {
    private final UserInfoDao userInfoDao;

    public ResponseUserInfo selectUserInfo(Long id) {
        UserInfo userInfo = userInfoDao.find(id);
        return toResponseUserInfo(userInfo);
    }

    public ResponseUserInfo selectUserInfo(String name) {
        UserInfo userInfo = userInfoDao.findByName(name);
        return toResponseUserInfo(userInfo);
    }

    public ResponseUserInfo selectUserInfo(UserInfo userInfo) {
        UserInfo userInfo2 = userInfoDao.findByNameAndPassword(userInfo);
        return toResponseUserInfo(userInfo2);
    }

    private ResponseUserInfo toResponseUserInfo(UserInfo userInfo) {
        if(userInfo == null) {
            return null;
        }
        return new ResponseUserInfo(userInfo.getId(), userInfo.getName(), userInfo.getGradeCode());
    }
}
