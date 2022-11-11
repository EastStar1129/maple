package com.nexon.maple.terms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexon.maple.terms.dto.ResponseTermsInfoDTO;
import com.nexon.maple.terms.type.TermsType;
import com.nexon.maple.userInfo.dto.RegisterUserInfoDTO;
import com.nexon.maple.userInfo.service.UserInfoWriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@AutoConfigureMybatis
class TermsInfoServiceTest {
    private Long code = 1L;
    private Long userId = 1L;

    @Autowired
    private TermsInfoWriteService termsInfoWriteService;

    @Autowired
    private TermsAgreeInfoWriteService termsAgreeInfoWriteService;

    @Autowired
    private TermsInfoReadService termsInfoReadService;

    @Autowired
    private UserInfoWriteService userInfoWriteService;

    @BeforeEach
    @Transactional
    void setUserAndTerms() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", "GM");
        input.put("password", "12341234");
        input.put("otpNumber", "12341234");
        input.put("pageNumber", 1L);
        input.put("terms", List.of("1"));

        //when
        ObjectMapper om = new ObjectMapper();
        RegisterUserInfoDTO registerUserInfoDTO = om.convertValue(input, RegisterUserInfoDTO.class);

        code = termsInfoWriteService.saveLoginType("test", "test").getCode();
        userId = userInfoWriteService.regist(registerUserInfoDTO).getId();
    }

    @Test
    @Transactional
    void 동의이력_저장() {
        termsAgreeInfoWriteService.saveTermsAgreeInfo(code, userId);
    }

    @Test
    @Transactional
    void 동의이력_리스트_저장() {
        termsAgreeInfoWriteService.saveAllTermsAgreeInfo(List.of(String.valueOf(code)), userId);
    }

    @Test
    @Transactional
    void 약관내역_조회() {
        termsInfoReadService.selectTerms(TermsType.LOGIN.getTitle())
                .contains(ResponseTermsInfoDTO.builder().code(code).build());
    }
}