package com.nexon.maple.otp.repository;

import com.nexon.maple.otp.entity.Otp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OtpDao {
    //otp 발급
    int save(Otp otp);

    //조회 조회
    Otp findByUserName(String userName);
}
