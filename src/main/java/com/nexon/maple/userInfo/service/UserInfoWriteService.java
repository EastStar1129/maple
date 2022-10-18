package com.nexon.maple.userInfo.service;

import com.nexon.maple.comm.maplestoryHomepage.CustomMapleComment;
import com.nexon.maple.otp.entity.Otp;
import com.nexon.maple.otp.repository.OtpDao;
import com.nexon.maple.terms.entity.TermsAgreeInfo;
import com.nexon.maple.terms.entity.TermsInfo;
import com.nexon.maple.terms.repository.TermsInfoDao;
import com.nexon.maple.userInfo.dto.RegisterUserInfoCommand;
import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoWriteService {
    private final UserInfoDao userInfoDao;
    private final OtpDao otpDao;
    private final TermsInfoDao termsInfoDao;

    @Transactional
    public void regist(UserInfo userInfo){
        int saveResult = userInfoDao.save(userInfo);
        Assert.isTrue(saveResult == 1, "회원가입이 되지 않았습니다.");
    }
}
