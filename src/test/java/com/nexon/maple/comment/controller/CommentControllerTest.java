package com.nexon.maple.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexon.maple.comment.service.CommentWriteService;
import com.nexon.maple.config.security.jwt.JwtToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentWriteService commentWriteService;

    @Test
    public void 댓글조회() throws Exception{
        String url = "/1/comments";
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "test", authorities = {"ROLE_1"})
    @Transactional
    public void 댓글등록() throws Exception{
        //given
        String url = "/comments";
        Map<String, String> input = new HashMap<>();
        input.put("id", "1");
        input.put("comment", "댓글작성 테스트");

        //when-then
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(objectMapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void 로그인을하지않으면_댓글등록_실패() throws Exception{
        //given
        String url = "/comments";
        Map<String, String> input = new HashMap<>();
        input.put("id", "1");
        input.put("comment", "댓글작성 테스트");

        //when-then
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(objectMapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }
}