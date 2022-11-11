package com.nexon.maple.character.service;

import com.nexon.maple.character.dto.ResponseCharacterInfoDTO;
import com.nexon.maple.character.entity.CharacterInfo;
import com.nexon.maple.util.maplestoryHomepage.CustomMapleCharacter;
import com.nexon.maple.util.maplestoryHomepage.object.MapleCharacter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SelectCharacterInfoService {
    private final CharacterInfoReadService characterInfoReadService;
    private final CharacterInfoWriteService characterInfoWriteService;

    @Transactional
    public ResponseCharacterInfoDTO findCharacter(String characterName) {
        // 1. DB 조회
        CharacterInfo characterInfo = characterInfoReadService.select(characterName);
        if(Objects.nonNull(characterInfo)) {
            return ResponseCharacterInfoDTO.of(characterInfo);
        }

        // 2. 조회 데이터가 없는 경우 크롤링
        MapleCharacter mapleCharacter = getMapleCharacter(characterName);
        if(Objects.isNull(mapleCharacter)) {
            return null;
        }

        // 3. 크롤링 데이터가 있으면 저장
        CharacterInfo saveCharacterInfo = characterInfoWriteService.save(mapleCharacter);
        return ResponseCharacterInfoDTO.of(saveCharacterInfo);
    }

    private MapleCharacter getMapleCharacter(String userName) {
        return new CustomMapleCharacter(userName).getMapleCharacter();
    }
}
