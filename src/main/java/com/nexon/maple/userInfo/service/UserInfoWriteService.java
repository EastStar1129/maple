package com.nexon.maple.userInfo.service;

import com.nexon.maple.comm.maplestoryHomepage.CustomMapleComment;
import com.nexon.maple.otp.entity.Otp;
import com.nexon.maple.otp.repository.OtpDao;
import com.nexon.maple.terms.entity.TermsAgreeInfo;
import com.nexon.maple.terms.entity.TermsInfo;
import com.nexon.maple.terms.repository.TermsInfoDao;
import com.nexon.maple.userInfo.dto.RegisterUserInfoCommand;
import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoWriteService {
    private final UserInfoDao userInfoDao;
    private final OtpDao otpDao;
    private final TermsInfoDao termsInfoDao;

    @Transactional
    public void register(RegisterUserInfoCommand registerUserInfoCommand){
        /*
            목표 : 회원정보(이름, 패스워드)를 받아서 등록한다.
                - 이름은 10자리 미만이다.
                - 패스워드는 8 ~ 15 자리이다.
                - 패스워드는 단방향 암호화가 적용된다.
                - 약관동의를 받는다.
                - 필수약관은 전부 체크되어야 한다.
            파라미터 - memberRegisterCommand
            val userInfo = UserInfo.of(RegisterUserInfo)
            userInfoRepository.save(userInfo)
        */
        UserInfo userInfo = UserInfo.builder()
                .name(registerUserInfoCommand.name())
                .password(registerUserInfoCommand.password())
                .build();

        //중복아이디 체크
        validateDuplicateName(registerUserInfoCommand.name());

        //OTP 조회 여부
        validateOtpNumber(registerUserInfoCommand);
        //약관 동의 검사
        validateTerms(registerUserInfoCommand.terms());

        saveUserInfo(userInfo);
        saveAllTermsInfo(registerUserInfoCommand.terms(), userInfo.getId());
    }

    private void saveUserInfo(UserInfo userInfo) {
        int saveResult = userInfoDao.save(userInfo);
        Assert.isTrue(saveResult == 1, "회원가입이 되지 않았습니다.");
    }

    private void validateDuplicateName(String name) {
        Assert.isNull(userInfoDao.findByName(name), "중복된 아이디가 존재합니다.");
    }

    private void validateOtpNumber(RegisterUserInfoCommand registerUserInfoCommand) {
        /*
            1. 이름에 해당하는 OTP 번호 중 가장 마지막에 발급 된 OTP 번호를 조회한다.
            2. 조회된 OTP 번호와 받은 이름, 페이지번호로 댓글 게시판을 검색한다.
        */
        String otpNumber = findByOtpNumber(registerUserInfoCommand.name());

        CustomMapleComment customComment =
                new CustomMapleComment(registerUserInfoCommand.name(), registerUserInfoCommand.pageNumber());
        Assert.isTrue(customComment.equalsComment(otpNumber), "OTP 번호가 일치하지 않습니다.");
    }

    private String findByOtpNumber(String userName) {
        Otp otp = otpDao.findByUserName(userName);
        Assert.notNull(otp, "OTP 번호가 발급되지 않았습니다.");
        return otp.getOtpNumber();
    }

    private void validateTerms(List<String> terms) {
        List<TermsInfo> list = termsInfoDao.findByType("로그인");
        long count = list.stream()
                .filter(termsInfo -> termsInfo.getRequiredTerms().equals("Y"))
                .filter(termsInfo -> terms.contains(String.valueOf(termsInfo.getCode())))
                .count();

        Assert.isTrue(count == terms.size(), "필수약관에 대한 동의여부가 없습니다.");
    }

    private void saveAllTermsInfo(List<String> termsList, Long userId) {
        for(String terms: termsList) {
            saveTermsInfo(Long.parseLong(terms), userId);
        }
    }

    private void saveTermsInfo(Long terms, Long userId) {
        TermsAgreeInfo termsAgreeInfo = TermsAgreeInfo.builder()
                .userId(userId)
                .termsCode(terms)
                .agreeYn("Y")
                .build();

        Assert.isTrue(termsInfoDao.save(termsAgreeInfo) == 1, "약관동의여부가 저장되지 않았습니다.");
    }
}
