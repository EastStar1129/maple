package com.nexon.maple.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexon.maple.config.security.auth.PrincipalDetails;
import com.nexon.maple.config.security.jwt.JwtToken;
import com.nexon.maple.login.service.LoginService;
import com.nexon.maple.userInfo.dto.RegisterUserInfoCommand;
import com.nexon.maple.userInfo.entity.UserInfo;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/*
    UsernamePasswordAuthenticationFilter
    로그인을 위한 필터

    ** 생성자 **
    setFilterProcessesUrl("/user/login") : login url 변경

    ** attemptAuthentication Override **
    1. request 값 추출
    2. ID, PASSWORD validate
    2-1 검증실패 : validate exception
    2-2 검증완료 : 3
    3. security에서 확인 가능한 토큰으로 변경 ( UsernamePasswordAuthenticationToken )
    4. 반환된 객체는 AuthenticationManager 인증에 사용된다.

    return : Authentication Type

    ** successfulAuthentication Override **
    인증 성공 시 토큰 발급

    ** unsuccessfulAuthentication Override **
    인증 실패 시 헤더값에 custom 에러를 던져줄 지 결정
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;
    private final LoginService loginService;

    private static final String LOGIN_URL = "/login";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtToken jwtToken, LoginService loginService) {
        this.loginService = loginService;
        setFilterProcessesUrl(LOGIN_URL);
        this.authenticationManager = authenticationManager;
        this.jwtToken = jwtToken;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        ObjectMapper om = new ObjectMapper();
        RegisterUserInfoCommand command;

        command = om.readValue(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8),
                RegisterUserInfoCommand.class);

        UserInfo userInfo = UserInfo.builder()
                .name(command.name())
                .password(command.password())
                .build();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userInfo.getName(), userInfo.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        loginService.addHeaderToken(response, principalDetails);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
