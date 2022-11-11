package com.nexon.maple.character.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexon.maple.character.dto.ResponseCharacterInfoDTO;
import com.nexon.maple.userInfo.dto.RegisterUserInfoDTO;
import com.nexon.maple.userInfo.service.UserInfoWriteService;
import com.nexon.maple.util.maplestoryHomepage.CustomMapleCharacter;
import com.nexon.maple.util.maplestoryHomepage.object.MapleCharacter;
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
class SelectCharacterInfoServiceTest {

    private Long characterId = 1L;

    @Autowired
    private SelectCharacterInfoService selectCharacterInfoService;

    @Autowired
    private UserInfoWriteService userInfoWriteService;

    @Autowired
    private CharacterInfoWriteService characterInfoWriteService;

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
    public void test() {
        //given
        String characterName = "구로5동호영";

        //when
        ResponseCharacterInfoDTO responseCharacterInfoDTO = selectCharacterInfoService.findCharacter(characterName);

        //then
//        assertEquals(responseCharacterInfoDTO);
    }
}