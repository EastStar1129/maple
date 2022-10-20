package com.nexon.maple.userInfo.service;

import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class UserInfoWriteService {
    private final UserInfoDao userInfoDao;

    @Transactional
    public void regist(UserInfo userInfo){
        int saveResult = userInfoDao.save(userInfo);
        Assert.isTrue(saveResult == 1, "회원가입이 되지 않았습니다.");
    }
}
