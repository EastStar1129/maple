package com.nexon.maple.character.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexon.maple.character.dto.ResponseCharacterInfoDTO;
import com.nexon.maple.userInfo.dto.RegisterUserInfoDTO;
import com.nexon.maple.userInfo.service.UserInfoWriteService;
import com.nexon.maple.util.maplestoryHomepage.CustomMapleCharacter;
import com.nexon.maple.util.maplestoryHomepage.object.MapleCharacter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@AutoConfigureMybatis
class SelectCharacterInfoServiceTest {
    private static final String CHARACTER_NAME = "구로5동호영";

    @Autowired
    private SelectCharacterInfoService selectCharacterInfoService;

    @Autowired
    private UserInfoWriteService userInfoWriteService;

    @Autowired
    private CharacterInfoWriteService characterInfoWriteService;

    @BeforeEach
    @Transactional
    void setUser() throws IOException {
        //given
        Map<String, Object> input = new HashMap<>();
        input.put("name", "GM");
        input.put("password", "12341234");
        input.put("otpNumber", "12341234");
        input.put("pageNumber", 1L);
        input.put("terms", List.of("1"));
        ObjectMapper om = new ObjectMapper();
        RegisterUserInfoDTO registerUserInfoDTO = om.convertValue(input, RegisterUserInfoDTO.class);

        //when
        MapleCharacter mapleCharacter = new CustomMapleCharacter(CHARACTER_NAME).build();

        //then
        userInfoWriteService.regist(registerUserInfoDTO).getId();
        characterInfoWriteService.save(mapleCharacter);
    }

    @ParameterizedTest
    @ValueSource(strings = {"구로5동호영", "심밧드의삶"})
    @Transactional
    public void 존재하는캐릭터_조회(String characterName) {
        //when
        ResponseCharacterInfoDTO responseCharacterInfoDTO = selectCharacterInfoService.findCharacter(characterName);

        //then
        Assertions.assertNotNull(responseCharacterInfoDTO);
    }
    @Test
    @Transactional
    public void 존재하지않는캐릭터_조회() {
        //given
        String characterName = "GM";

        //when
        ResponseCharacterInfoDTO responseCharacterInfoDTO = selectCharacterInfoService.findCharacter(characterName);

        //then
        Assertions.assertNull(responseCharacterInfoDTO);
    }
}