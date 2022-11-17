package com.nexon.maple.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexon.maple.config.security.jwt.JwtToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtToken jwtToken;

    @Test
    @Transactional
    public void 로그인() throws Exception {
        //given
        String url = "/login";
        Map<String, String> input = new HashMap<>();
        input.put("name", "test");
        input.put("password", "12341234");

        //when
        List<String> cookies = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(objectMapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getHeaders("Set-Cookie");

        Long count = cookies.stream()
                .filter(cookie -> cookie.startsWith(jwtToken.getAccessTokenName())
                        || cookie.startsWith(jwtToken.getRefreshTokenName())
                        || cookie.startsWith(jwtToken.getTokenFlagName()))
                .count();

        //then
        Assertions.assertEquals(count, 3);
    }

    @Test
    @Transactional
    public void 로그인이후_토큰검증() throws Exception {
        //given
        String loginURL = "/login";
        String tokenURL = "/effectiveToken";
        String userName = "test";
        Map<String, String> input = new HashMap<>();
        input.put("name", userName);
        input.put("password", "12341234");

        //when
        List<String> cookies = mockMvc.perform(MockMvcRequestBuilders.post(loginURL)
                        .content(objectMapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getHeaders("Set-Cookie");


        String cookie1String = cookies.stream().filter(cookie -> cookie.startsWith(jwtToken.getAccessTokenName())).collect(Collectors.toList()).get(0);
        String cookie2String = cookies.stream().filter(cookie -> cookie.startsWith(jwtToken.getRefreshTokenName())).collect(Collectors.toList()).get(0);
        String cookie3String = cookies.stream().filter(cookie -> cookie.startsWith(jwtToken.getTokenFlagName())).collect(Collectors.toList()).get(0);

        Cookie cookie1 = new Cookie(jwtToken.getAccessTokenName(), cookie1String.split(";")[0].split("=")[1]);
        cookie1.setHttpOnly(true);
        cookie1.setSecure(true);
        Cookie cookie2 = new Cookie(jwtToken.getRefreshTokenName(), cookie2String.split(";")[0].split("=")[1]);
        cookie2.setHttpOnly(true);
        cookie2.setSecure(true);
        Cookie cookie3 = new Cookie(jwtToken.getTokenFlagName(), cookie3String.split(";")[0].split("=")[1]);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(tokenURL)
                        .cookie(cookie1, cookie2, cookie3));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(userName))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    @Transactional
    public void 로그인없이_토큰검증() throws Exception {
        //given
        String tokenURL = "/effectiveToken";

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(tokenURL));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

}