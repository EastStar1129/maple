package com.nexon.maple.view.service;

import com.nexon.maple.terms.dto.ResponseTermsInfoDTO;
import com.nexon.maple.terms.type.TermsType;
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

    public List<ResponseTermsInfoDTO> selectLoginTermsList() {
        return findByTypeToLogin();
    }

    public List<ResponseTermsInfoDTO> findByTypeToLogin() {
        List<TermsInfo> list = termsInfoDao.findAllByType(TermsType.LOGIN.getTitle());

        return list.stream()
                .map(ResponseTermsInfoDTO::new)
                .collect(Collectors.toList());
    }
}
