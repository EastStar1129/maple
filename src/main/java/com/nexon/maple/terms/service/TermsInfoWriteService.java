package com.nexon.maple.terms.service;

import com.nexon.maple.terms.entity.TermsInfo;
import com.nexon.maple.terms.repository.TermsInfoDao;
import com.nexon.maple.terms.type.TermsType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class TermsInfoWriteService {
    private final TermsInfoDao termsInfoDao;

    @Transactional
    public TermsInfo saveLoginType(String name, String content) {
        TermsInfo termsInfo = TermsInfo.ofRequiredTerms(TermsType.LOGIN.getTitle(), name, content);
        termsInfoDao.save(termsInfo);
        Assert.notNull(termsInfo.getCode(), "약관정보가 저장되지 않았습니다.");
        return termsInfo;
    }
}
