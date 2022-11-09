package com.nexon.maple.character.service;

import com.nexon.maple.character.entity.CharacterInfo;
import com.nexon.maple.util.maplestoryHomepage.CustomMapleCharacter;
import com.nexon.maple.util.maplestoryHomepage.object.MapleCharacter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMybatis
class CharacterInfoWriteServiceTest {

    @Autowired
    private CharacterInfoWriteService characterInfoWriteService;

    @Test
    @Transactional
    void 캐릭터정보저장() {
        //given
        String characterName = "구로5동호영";

        //when
        MapleCharacter mapleCharacter = new CustomMapleCharacter(characterName).getMapleCharacter();

        CharacterInfo characterInfo = CharacterInfo.builder()
                .image(mapleCharacter.getImage())
                .characterRank(mapleCharacter.getRank())
                .rankMove(mapleCharacter.getRankMove())
                .name(mapleCharacter.getUserName())
                .job1(mapleCharacter.getJob1())
                .job2(mapleCharacter.getJob2())
                .characterLevel(mapleCharacter.getLevel())
                .experience(mapleCharacter.getExperience())
                .popularity(mapleCharacter.getPopularity())
                .guildName(mapleCharacter.getGuildName())
                .build();

        //then
        characterInfoWriteService.save(characterInfo);
    }

    @Test
    @Transactional
    void 캐릭터정보저장_실패() {
        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> characterInfoWriteService.save(null));
    }


}