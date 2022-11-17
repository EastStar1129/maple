package com.nexon.maple.character.service;

import com.nexon.maple.util.maplestoryHomepage.CustomMapleCharacter;
import com.nexon.maple.util.maplestoryHomepage.object.MapleCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@SpringBootTest
@AutoConfigureMybatis
class CharacterInfoServiceTest {
    @Autowired
    private CharacterInfoReadService characterInfoReadService;

    @Autowired
    private CharacterInfoWriteService characterInfoWriteService;

    @BeforeEach
    @Transactional
    void 캐릭터정보저장() throws IOException {
        //given
        String characterName = "구로5동호영";

        //when
        MapleCharacter mapleCharacter = new CustomMapleCharacter(characterName).build();

        //then
        characterInfoWriteService.save(mapleCharacter);
    }

    @Test
    @Transactional
    public void 캐릭터조회() {
        //given
        String characterName = "구로5동호영";

        //then
        characterInfoReadService.select(characterName);
    }
}