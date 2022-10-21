package com.nexon.maple.terms.service;

import com.nexon.maple.terms.entity.TermsAgreeInfo;
import com.nexon.maple.terms.repository.TermsInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TermsInfoWriteService {
    private final TermsInfoDao termsInfoDao;

    public void saveAllTermsInfo(List<String> termsList, Long userId) {
        for(String terms: termsList) {
            saveTermsInfo(Long.parseLong(terms), userId);
        }
    }

    public void saveTermsInfo(Long terms, Long userId) {
        TermsAgreeInfo termsAgreeInfo = TermsAgreeInfo.builder()
                .userId(userId)
                .termsCode(terms)
                .agreeYn("Y")
                .build();

        Assert.isTrue(termsInfoDao.save(termsAgreeInfo) == 1, "약관동의여부가 저장되지 않았습니다.");
    }
}
