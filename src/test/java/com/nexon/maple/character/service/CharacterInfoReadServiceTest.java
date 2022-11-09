package com.nexon.maple.character.service;

import com.nexon.maple.character.repository.CharacterInfoDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CharacterInfoReadServiceTest {

    @MockBean
    private CharacterInfoReadService characterInfoReadService;

    @Mock
    private CharacterInfoDao characterInfoDao;

    @Test
    public void 캐릭터조회() {
        //given
        String characterName = "구로5동호영";
        //then
        characterInfoReadService.select(characterName);
    }
}