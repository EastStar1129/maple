package com.nexon.maple.userInfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexon.maple.userInfo.service.RegisteUserInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisteUserInfoService registeUserInfoService;

    @Test
    @Transactional
    public void 회원가입() throws Exception {
        //given
        String url = "/users";
        Map<String, Object> input = new HashMap<>();
        input.put("name", "test");
        input.put("password", "12341234");
        input.put("otpNumber", "12341234");
        input.put("pageNumber", 1L);
        input.put("terms", List.of("1"));

        //when
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(url)
                .content(objectMapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andDo(MockMvcResultHandlers.print());
    }
}