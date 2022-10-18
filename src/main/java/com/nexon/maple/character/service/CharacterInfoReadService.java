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
public class CharacterInfoReadService {
    private final CharacterInfoDao characterInfoDao;

    public CharacterInfo select(String userName) {
        CharacterInfo characterInfo = CharacterInfo.builder()
                .name(userName)
                .build();

        return characterInfoDao.findByUserName(characterInfo);    }
}
