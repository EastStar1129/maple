package com.nexon.maple.character.service;

import com.nexon.maple.character.entity.CharacterInfo;
import com.nexon.maple.character.repository.CharacterInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
