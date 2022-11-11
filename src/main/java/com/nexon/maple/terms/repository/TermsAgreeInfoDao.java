package com.nexon.maple.terms.repository;


import com.nexon.maple.terms.entity.TermsAgreeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TermsAgreeInfoDao {
    int save(TermsAgreeInfo termsAgreeInfo);
}
