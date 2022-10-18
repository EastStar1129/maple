package com.nexon.maple.character.service;

import com.nexon.maple.character.dto.ResponseCharacterInfo;
import com.nexon.maple.character.entity.CharacterInfo;
import com.nexon.maple.comm.maplestoryHomepage.CustomMapleCharacter;
import com.nexon.maple.comm.maplestoryHomepage.object.MapleCharacter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SelectCharacterInfoService {
    private final CharacterInfoReadService characterInfoReadService;
    private final CharacterInfoWriteService characterInfoWriteService;

    @Transactional
    public ResponseCharacterInfo select(String userName) {
        /*
            1. DB 조회
            2. 조회 데이터가 없는 경우 크롤링
            3. 크롤링 데이터가 있으면 저장
         */
        // 1
        var characterInfo = characterInfoReadService.select(userName);
        if(characterInfo != null) {
            return toResponseCharacterInfo(characterInfo);
        }

        // 2
        MapleCharacter mapleCharacter = getMapleCharacter(userName);
        if(mapleCharacter == null) {
            return null;
        }

        // 3
        var saveCharacterInfo = toCharacterInfo(mapleCharacter);
        characterInfoWriteService.save(saveCharacterInfo);

        return toResponseCharacterInfo(saveCharacterInfo);
    }

    private CharacterInfo toCharacterInfo(MapleCharacter mapleCharacter) {
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
        return characterInfo;
    }

    private MapleCharacter getMapleCharacter(String userName) {
        return new CustomMapleCharacter(userName).getMapleCharacter();
    }

    public ResponseCharacterInfo toResponseCharacterInfo(CharacterInfo characterInfo) {
        return ResponseCharacterInfo.builder()
                .id(characterInfo.getId())
                .image(characterInfo.getImage())
                .rank(characterInfo.getCharacterRank())
                .rankMove(characterInfo.getRankMove())
                .userName(characterInfo.getName())
                .job1(characterInfo.getJob1())
                .job2(characterInfo.getJob2())
                .level(characterInfo.getCharacterLevel())
                .experience(characterInfo.getExperience())
                .popularity(characterInfo.getPopularity())
                .guildName(characterInfo.getGuildName())
                .build();
    }
}
