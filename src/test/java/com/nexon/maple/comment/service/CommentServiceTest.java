package com.nexon.maple.comment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexon.maple.character.service.CharacterInfoWriteService;
import com.nexon.maple.comment.dto.WriteCommentDTO;
import com.nexon.maple.userInfo.dto.RegisterUserInfoDTO;
import com.nexon.maple.userInfo.service.UserInfoWriteService;
import com.nexon.maple.util.maplestoryHomepage.CustomMapleCharacter;
import com.nexon.maple.util.maplestoryHomepage.object.MapleCharacter;
import io.jsonwebtoken.lang.Assert;
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
class CommentServiceTest {
    private Long characterId = 1L;

    @Autowired
    private UserInfoWriteService userInfoWriteService;

    @Autowired
    private CharacterInfoWriteService characterInfoWriteService;

    @Autowired
    private CommentWriteService commentWriteService;

    @Autowired
    private CommentReadService commentReadService;

    @BeforeEach
    @Transactional
    void setUser() {
        //given
        String characterName = "구로5동호영";

        Map<String, Object> input = new HashMap<>();
        input.put("name", "GM");
        input.put("password", "12341234");
        input.put("otpNumber", "12341234");
        input.put("pageNumber", 1L);
        input.put("terms", List.of("1"));
        ObjectMapper om = new ObjectMapper();
        RegisterUserInfoDTO registerUserInfoDTO = om.convertValue(input, RegisterUserInfoDTO.class);

        //when
        MapleCharacter mapleCharacter = new CustomMapleCharacter(characterName).getMapleCharacter();

        //then
        userInfoWriteService.regist(registerUserInfoDTO).getId();
        characterId = characterInfoWriteService.save(mapleCharacter).getId();
    }

    @Test
    @Transactional
    public void 댓글작성_조회() {
        //given
        String userName = "GM";
        Map<String, Object> input = new HashMap<>();
        input.put("characterId", characterId);
        input.put("comment", "댓글등록 테스트");
        WriteCommentDTO writeCommentDTO = new ObjectMapper().convertValue(input, WriteCommentDTO.class);

        //when
        commentWriteService.saveComment(userName, writeCommentDTO);

        //then
        Assert.notEmpty(commentReadService.selectComment(characterId));
    }

}