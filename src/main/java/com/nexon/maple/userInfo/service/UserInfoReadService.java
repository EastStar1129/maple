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
        Assert.notNull(userInfo, "회원정보가 없습니다.");
        return toResponseUserInfo(userInfo);
    }

    private ResponseUserInfo toResponseUserInfo(UserInfo userInfo) {
        return new ResponseUserInfo(userInfo.getId(), userInfo.getName(), userInfo.getGradeCode());
    }
}
