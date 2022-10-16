package com.nexon.maple.character.repository;

import com.nexon.maple.character.entity.CharacterInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CharacterInfoDao {
    CharacterInfo findByUserName(CharacterInfo characterInfo);

    int save(CharacterInfo characterInfo);
}
