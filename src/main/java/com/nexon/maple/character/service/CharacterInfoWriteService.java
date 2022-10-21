package com.nexon.maple.character.service;

import com.nexon.maple.character.entity.CharacterInfo;
import com.nexon.maple.character.repository.CharacterInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class CharacterInfoWriteService {
    private final CharacterInfoDao characterInfoDao;

    public void save(CharacterInfo characterInfo) {
        Assert.isTrue(characterInfoDao.save(characterInfo) == 1, "저장되지 않았습니다.");
    }
}
