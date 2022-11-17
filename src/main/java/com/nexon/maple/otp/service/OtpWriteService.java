package com.nexon.maple.otp.service;


import com.nexon.maple.otp.entity.Otp;
import com.nexon.maple.otp.repository.OtpDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class OtpWriteService {
    private final OtpDao otpDao;

    //otp 발급
    public String createOtp(String userName) {
        Otp otp = Otp.of(userName);
        return saveOtp(otp).getOtpNumber();
    }

    public Otp saveOtp(Otp otp) {
        otpDao.save(otp);
        Assert.notNull(otp.getIdx(), "OTP 발급에 실패했습니다.");

        return otp;
    }

}
