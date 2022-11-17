package com.nexon.maple.comment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexon.maple.character.service.CharacterInfoWriteService;
import com.nexon.maple.comment.dto.WriteCommentDTO;
import com.nexon.maple.userInfo.dto.RegisterUserInfoDTO;
import com.nexon.maple.userInfo.service.UserInfoReadService;
import com.nexon.maple.userInfo.service.UserInfoWriteService;
import com.nexon.maple.util.CustomBean;
import com.nexon.maple.util.maplestoryHomepage.CustomMapleCharacter;
import com.nexon.maple.util.maplestoryHomepage.object.MapleCharacter;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMybatis
class CommentServiceTest {
    private String characterName;

    @Autowired
    private UserInfoWriteService userInfoWriteService;

    @Autowired
    private UserInfoReadService userInfoReadService;

    @Autowired
    private CharacterInfoWriteService characterInfoWriteService;

    @Autowired
    private CommentWriteService commentWriteService;

    @Autowired
    private CommentReadService commentReadService;

    @BeforeEach
    @Transactional
    void setUser() throws IOException {
        //given
        String userName = "GM";
        String characterName = "구로5동혀로";
        RegisterUserInfoDTO registerUserInfoDTO = CustomBean.ofRegisterUserInfoDTO(userName, "12341234");

        //when
        MapleCharacter mapleCharacter = new CustomMapleCharacter(characterName).build();

        //then
        CustomBean.회원가입(userInfoWriteService, userInfoReadService, registerUserInfoDTO);
        this.characterName = characterInfoWriteService.save(mapleCharacter).getName();
    }

    @Test
    @Transactional
    public void 댓글작성_조회() {
        //given
        String userName = "GM";
        Map<String, Object> input = new HashMap<>();
        input.put("characterName", characterName);
        input.put("comment", "댓글등록 테스트");
        WriteCommentDTO writeCommentDTO = new ObjectMapper().convertValue(input, WriteCommentDTO.class);

        //when
        commentWriteService.saveComment(userName, writeCommentDTO);

        //then
        Assert.notEmpty(commentReadService.selectComment(characterName));
    }

}