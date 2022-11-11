package com.nexon.maple.terms.service;

import com.nexon.maple.terms.dto.ResponseTermsInfoDTO;
import com.nexon.maple.terms.entity.TermsInfo;
import com.nexon.maple.terms.repository.TermsInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TermsInfoReadService {
    private final TermsInfoDao termsInfoDao;

    public List<ResponseTermsInfoDTO> selectTerms(String type) {
        List<TermsInfo> list = termsInfoDao.findAllByType(type);
        List<ResponseTermsInfoDTO> responseList = new ArrayList<>();

        for(TermsInfo termsInfo: list) {
            responseList.add(ResponseTermsInfoDTO.of(termsInfo));
        }

        return responseList;
    }
}
