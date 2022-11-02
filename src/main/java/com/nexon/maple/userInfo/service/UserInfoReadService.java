package com.nexon.maple.userInfo.service;

import com.nexon.maple.userInfo.dto.ResponseUserInfoDTO;
import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserInfoReadService {
    private final UserInfoDao userInfoDao;

    public ResponseUserInfoDTO selectUserInfo(Long id) {
        UserInfo userInfo = userInfoDao.find(id);
        return ofResponseUserInfo(userInfo);
    }

    public ResponseUserInfoDTO selectUserInfo(String name) {
        UserInfo userInfo = userInfoDao.findByName(name);
        return ofResponseUserInfo(userInfo);
    }

    public ResponseUserInfoDTO selectUserInfo(UserInfo userInfo) {
        UserInfo userInfo2 = userInfoDao.findByNameAndPassword(userInfo);
        return ofResponseUserInfo(userInfo2);
    }

    private ResponseUserInfoDTO ofResponseUserInfo(UserInfo userInfo) {
        if(userInfo == null) {
            return null;
        }
        return new ResponseUserInfoDTO(userInfo.getId(), userInfo.getName(), userInfo.getGradeCode());
    }
}
