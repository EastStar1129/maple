package com.nexon.maple.terms.service;

import com.nexon.maple.terms.entity.TermsAgreeInfo;
import com.nexon.maple.terms.repository.TermsAgreeInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TermsAgreeInfoWriteService {
    private final TermsAgreeInfoDao termsAgreeInfoDao;

    @Transactional
    public void saveAllTermsAgreeInfo(List<String> termsList, Long userId) {
        for(String terms: termsList) {
            saveTermsAgreeInfo(Long.parseLong(terms), userId);
        }
    }

    @Transactional
    public Long saveTermsAgreeInfo(Long terms, Long userId) {
        TermsAgreeInfo termsAgreeInfo = TermsAgreeInfo.ofAgree(terms, userId);
        termsAgreeInfoDao.save(termsAgreeInfo);
        Assert.notNull(termsAgreeInfo.getIdx(), "약관동의여부가 저장되지 않았습니다.");
        return termsAgreeInfo.getIdx();
    }

    @Transactional
    public void cancelTermsAgree(Long idx) {
        TermsAgreeInfo termsAgreeInfo = TermsAgreeInfo.ofCancel(idx);
        Assert.isTrue(termsAgreeInfoDao.cancel(termsAgreeInfo) == 1, "약관정보가 수정되지 않았습니다.");
    }
}
