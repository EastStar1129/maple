package com.nexon.maple.terms.service;

import com.nexon.maple.terms.dto.ResponseTermsInfoDTO;
import com.nexon.maple.terms.type.TermsType;
import com.nexon.maple.userInfo.dto.RegisterUserInfoDTO;
import com.nexon.maple.userInfo.service.UserInfoReadService;
import com.nexon.maple.userInfo.service.UserInfoWriteService;
import com.nexon.maple.util.CustomBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Autowired
    private UserInfoReadService userInfoReadService;

    @BeforeEach
    @Transactional
    void setUserAndTerms() {
        //given
        RegisterUserInfoDTO registerUserInfoDTO = CustomBean.ofRegisterUserInfoDTO("GM", "12341234");

        //when-then
        code = termsInfoWriteService.saveLoginType("test", "test").getCode();
        userId = CustomBean.회원가입(userInfoWriteService, userInfoReadService, registerUserInfoDTO).id();
    }

    @Test
    @Transactional
    void 동의이력_저장() {
        //when
        termsAgreeInfoWriteService.saveTermsAgreeInfo(code, userId);
    }

    @Test
    @Transactional
    void 동의취소() {
        //given
        Long idx = termsAgreeInfoWriteService.saveTermsAgreeInfo(code, userId);

        //when-then
        termsAgreeInfoWriteService.cancelTermsAgree(idx);
    }

    @Test
    @Transactional
    void 동의취소시_없는이력() {
        //given
        Long idx = 0L;

        //when-then
        assertThrows(IllegalArgumentException.class, () -> termsAgreeInfoWriteService.cancelTermsAgree(idx));
    }


    @Test
    @Transactional
    void 동의이력_리스트_저장() {
        //when
        termsAgreeInfoWriteService.saveAllTermsAgreeInfo(List.of(String.valueOf(code)), userId);
    }

    @Test
    @Transactional
    void 약관내역_조회() {
        //given
        List<ResponseTermsInfoDTO> list = termsInfoReadService.selectTerms(TermsType.LOGIN.getTitle());

        //then
        assertTrue(list.contains(ResponseTermsInfoDTO.builder().code(code).build()));
    }
}