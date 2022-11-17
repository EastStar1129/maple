package com.nexon.maple.userInfo.service;

import com.nexon.maple.otp.service.OtpReadService;
import com.nexon.maple.terms.dto.ResponseTermsInfoDTO;
import com.nexon.maple.terms.service.TermsAgreeInfoWriteService;
import com.nexon.maple.terms.service.TermsInfoReadService;
import com.nexon.maple.terms.type.TermsType;
import com.nexon.maple.userInfo.dto.RegisterUserInfoDTO;
import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.util.maplestoryHomepage.CustomMapleComment;
import com.nexon.maple.util.maplestoryHomepage.object.MapleComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RegisteUserInfoService {
    private final TermsInfoReadService termsInfoReadService;
    private final TermsAgreeInfoWriteService termsAgreeInfoWriteService;
    private final UserInfoWriteService userInfoWriteService;
    private final OtpReadService otpReadService;

    @Transactional
    public void regist(RegisterUserInfoDTO registerUserInfoDTO) {
        // 1. 데이터 검증
        UserInfo.validation(registerUserInfoDTO.getPassword(), registerUserInfoDTO.getName());

        // 2. 비지니스 로직
        validationUserInfo(registerUserInfoDTO);

        // 3. 회원가입
        UserInfo userInfo = userInfoWriteService.regist(registerUserInfoDTO);
        termsAgreeInfoWriteService.saveAllTermsAgreeInfo(registerUserInfoDTO.getTerms(), userInfo.getId());
    }

    private void validationUserInfo(RegisterUserInfoDTO registerUserInfoCommand) {
        //OTP 조회 여부
        String otpNumber = otpReadService.selectOtpNumber(registerUserInfoCommand.getName());
        validateOtpNumber(registerUserInfoCommand, otpNumber);

        //약관 동의 검사
        List<ResponseTermsInfoDTO> termsInfoList = termsInfoReadService.selectTerms(TermsType.LOGIN.getTitle());
        validateTerms(termsInfoList, registerUserInfoCommand.getTerms());
    }

    private void validateTerms(List<ResponseTermsInfoDTO> termsInfoList, List<String> terms) {
        long count = termsInfoList.stream()
                .filter(termsInfo -> termsInfo.getRequiredTerms().equals("Y"))
                .count();

        terms = terms == null ? List.of() : terms;
        List<String> finalTerms = terms;

        long count2 = termsInfoList.stream()
                .filter(termsInfo -> termsInfo.getRequiredTerms().equals("Y"))
                .filter(termsInfo -> finalTerms.contains(String.valueOf(termsInfo.getCode())))
                .count();

        Assert.isTrue(count == count2, "필수약관에 대한 동의여부가 없습니다.");
    }

    private void validateOtpNumber(RegisterUserInfoDTO registerUserInfoCommand, String otpNumber) {
        /*
            1. 이름에 해당하는 OTP 번호 중 가장 마지막에 발급 된 OTP 번호를 조회한다.
            2. 조회된 OTP 번호와 받은 이름, 페이지번호로 댓글 게시판을 검색한다.
        */
        MapleComment mapleComment = null;
        try {
            mapleComment = new CustomMapleComment(registerUserInfoCommand.getName(), registerUserInfoCommand.getPageNumber()).build();
        } catch (IOException e) {
            throw new IllegalArgumentException("메이플 댓글조회중 오류가 발생했습니다.");
        }
        Assert.notNull(mapleComment, "댓글이 존재하지 않습니다.");
        Assert.isTrue(mapleComment.equalsCommentContent(otpNumber), "OTP 번호가 일치하지 않습니다.");
    }
}
