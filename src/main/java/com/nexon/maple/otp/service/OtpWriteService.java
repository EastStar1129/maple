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
        Otp otp = Otp.builder()
                .userName(userName)
                .build();

        saveOtp(otp);
        return otp.getOtpNumber();
    }

    private void saveOtp(Otp otp) {
        int saveResult = otpDao.save(otp);
        Assert.isTrue(saveResult == 1, "OTP 발급 에러");
    }

}
