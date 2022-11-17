package com.nexon.maple.otp.service;

import com.nexon.maple.otp.entity.Otp;
import com.nexon.maple.otp.repository.OtpDao;
import com.nexon.maple.userInfo.entity.UserName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class OtpReadService {
    private final OtpDao otpDao;

    public String selectOtpNumber(String userName) {
        Otp otp = otpDao.findByUserName(UserName.of(userName).getUserName());
        return Objects.isNull(otp) ? null : otp.getOtpNumber();
    }
}
