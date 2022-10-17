package com.nexon.maple.character.service;

import com.nexon.maple.character.dto.ResponseCharacterInfo;
import com.nexon.maple.character.entity.CharacterInfo;
import com.nexon.maple.character.repository.CharacterInfoDao;
import com.nexon.maple.comm.maplestoryHomepage.CustomMapleCharacter;
import com.nexon.maple.comm.maplestoryHomepage.object.MapleCharacter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class CharacterInfoService {
    private final CharacterInfoDao characterDao;

    public ResponseCharacterInfo selectCharacter(String userName) {
        /*
            1. 조회
            2. 없는 경우 메이플사이트 조회
                2-1 있으면 데이터 저장
            반환
         */
        return findByUserName(userName);
    }

    public ResponseCharacterInfo findByUserName(String userName) {
        CharacterInfo characterInfo = CharacterInfo.builder()
                .name(userName)
                .build();

        characterInfo = characterDao.findByUserName(characterInfo);

        if(characterInfo == null) {
            return save(new CustomMapleCharacter(userName).getMapleCharacter());
        }

        return toResponseCharacterInfo(characterInfo);
    }

    public ResponseCharacterInfo save(MapleCharacter mapleCharacter) {
        if(mapleCharacter == null) {
            return null;
        }

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

        Assert.isTrue(characterDao.save(characterInfo) == 1, "저장되지 않았습니다.");

        return toResponseCharacterInfo(characterInfo);
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
