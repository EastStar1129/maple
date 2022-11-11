package com.nexon.maple.character.service;

import com.nexon.maple.character.entity.CharacterInfo;
import com.nexon.maple.character.repository.CharacterInfoDao;
import com.nexon.maple.util.maplestoryHomepage.object.MapleCharacter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class CharacterInfoWriteService {
    private final CharacterInfoDao characterInfoDao;

    public CharacterInfo save(MapleCharacter mapleCharacter) {
        CharacterInfo characterInfo = CharacterInfo.of(mapleCharacter);

        characterInfoDao.save(characterInfo);
        Assert.notNull(characterInfo.getId(), "저장되지 않았습니다.");
        return characterInfo;
    }
}
