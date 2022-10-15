package com.nexon.maple.terms.repository;


import com.nexon.maple.terms.entity.TermsAgreeInfo;
import com.nexon.maple.terms.entity.TermsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TermsInfoDao {
    //이용약관 조회
    List<TermsInfo> findByType(String type);
    int save(TermsAgreeInfo termsAgreeInfo);
}
