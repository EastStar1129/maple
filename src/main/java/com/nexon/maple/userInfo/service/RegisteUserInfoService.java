package com.nexon.maple.userInfo.service;

import com.nexon.maple.util.maplestoryHomepage.CustomMapleComment;
import com.nexon.maple.otp.service.OtpReadService;
import com.nexon.maple.terms.dto.ResponseTermsInfo;
import com.nexon.maple.terms.dto.TermsType;
import com.nexon.maple.terms.service.TermsInfoReadService;
import com.nexon.maple.terms.service.TermsInfoWriteService;
import com.nexon.maple.userInfo.dto.RegisterUserInfoCommand;
import com.nexon.maple.userInfo.dto.ResponseUserInfo;
import com.nexon.maple.userInfo.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegisteUserInfoService {
    private final TermsInfoReadService termsInfoReadService;
    private final TermsInfoWriteService termsInfoWriteService;
    private final UserInfoWriteService userInfoWriteService;
    private final UserInfoReadService userInfoReadService;
    private final OtpReadService otpReadService;


    @Transactional
    public void regist(RegisterUserInfoCommand registerUserInfoCommand) {
        /*
            1. 데이터 검증
            2. 검증
            2-1. OTP 체크
            2-2-1 OTP는 메이플스토리 게시글 댓글과 DB에 마지막 OTP번호와 비교
            2-3. 약관동의 검사
            3. 저장
            3-1 회원가입
            3-2 약관동의여부 저장
         */
        UserInfo userInfo = UserInfo.builder()
                .name(registerUserInfoCommand.name())
                .password(registerUserInfoCommand.password())
                .build();

        //검증 로직
        validationUserInfo(registerUserInfoCommand);

        //저장
        regist(registerUserInfoCommand, userInfo);
    }

    private void regist(RegisterUserInfoCommand registerUserInfoCommand, UserInfo userInfo) {
        userInfoWriteService.regist(userInfo);
        termsInfoWriteService.saveAllTermsInfo(registerUserInfoCommand.terms(), userInfo.getId());
    }

    private void validationUserInfo(RegisterUserInfoCommand registerUserInfoCommand) {
        //중복아이디 체크
        ResponseUserInfo responseUserInfo = userInfoReadService.selectUserInfo(registerUserInfoCommand.name());
        Assert.isNull(responseUserInfo, "중복된 아이디가 존재합니다.");

        //OTP 조회 여부
        String otpNumber = otpReadService.selectOtpNumber(registerUserInfoCommand.name());
        validateOtpNumber(registerUserInfoCommand, otpNumber);

        //약관 동의 검사
        List<ResponseTermsInfo> termsInfoList = termsInfoReadService.selectTerms(TermsType.LOGIN.getTitle());
        validateTerms(termsInfoList, registerUserInfoCommand.terms());
    }

    private void validateTerms(List<ResponseTermsInfo> termsInfoList, List<String> terms) {
        long count = termsInfoList.stream()
                .filter(termsInfo -> termsInfo.getRequiredTerms().equals("Y"))
                .filter(termsInfo -> terms.contains(String.valueOf(termsInfo.getCode())))
                .count();

        Assert.isTrue(count == terms.size(), "필수약관에 대한 동의여부가 없습니다.");
    }

    private void validateOtpNumber(RegisterUserInfoCommand registerUserInfoCommand, String otpNumber) {
        /*
            1. 이름에 해당하는 OTP 번호 중 가장 마지막에 발급 된 OTP 번호를 조회한다.
            2. 조회된 OTP 번호와 받은 이름, 페이지번호로 댓글 게시판을 검색한다.
        */
        CustomMapleComment customComment =
                new CustomMapleComment(registerUserInfoCommand.name(), registerUserInfoCommand.pageNumber());
        Assert.isTrue(customComment.equalsComment(otpNumber), "OTP 번호가 일치하지 않습니다.");
    }
}
