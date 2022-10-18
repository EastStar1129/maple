package com.nexon.maple.otp.service;

import com.nexon.maple.otp.entity.Otp;
import com.nexon.maple.otp.repository.OtpDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class OtpReadService {
    private final OtpDao otpDao;

    public String selectOtpNumber(String userName) {
        Otp otp = otpDao.findByUserName(userName);
        return toOtp(otp);
    }

    private String toOtp(Otp otp){
        if(Objects.isNull(otp)) {
            return null;
        }
        return otp.getOtpNumber();
    }
}
