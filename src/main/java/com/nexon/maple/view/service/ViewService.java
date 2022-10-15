package com.nexon.maple.view.service;

import com.nexon.maple.terms.dto.ResponseTermsInfo;
import com.nexon.maple.terms.dto.TermsType;
import com.nexon.maple.terms.entity.TermsInfo;
import com.nexon.maple.terms.repository.TermsInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ViewService {
    private final TermsInfoDao termsInfoDao;

    public List<ResponseTermsInfo> selectLoginTermsList() {
        return findByTypeToLogin();
    }

    public List<ResponseTermsInfo> findByTypeToLogin() {
        List<TermsInfo> list = termsInfoDao.findByType(TermsType.LOGIN.getTitle());

        return list.stream()
                .map(ResponseTermsInfo::new)
                .collect(Collectors.toList());
    }
}
