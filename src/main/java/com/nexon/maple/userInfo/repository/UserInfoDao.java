package com.nexon.maple.userInfo.repository;

import com.nexon.maple.userInfo.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserInfoDao {
    /*
        유저를 조회한다.
     */
    UserInfo find(Long id);
    UserInfo findByName(String name);
    UserInfo findByNameAndPassword(UserInfo userInfo);

    /*
        회원을 저장한다.
    */
    int save(UserInfo userInfo);
}
