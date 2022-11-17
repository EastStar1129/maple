package com.nexon.maple.character.dto;

import com.nexon.maple.character.entity.CharacterInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseCharacterInfoDTO {
    Long id;
    String image;
    Long rank;
    String rankMove;
    String userName;
    String job1;
    String job2;
    String level;
    String experience;
    Long popularity;
    String guildName;

    @Builder
    public ResponseCharacterInfoDTO(Long id, String image, Long rank, String rankMove, String userName, String job1, String job2, String level, String experience, Long popularity, String guildName) {
        this.id = id;
        this.image = image;
        this.rank = rank;
        this.rankMove = rankMove;
        this.userName = userName;
        this.job1 = job1;
        this.job2 = job2;
        this.level = level;
        this.experience = experience;
        this.popularity = popularity;
        this.guildName = guildName;
    }

    public static ResponseCharacterInfoDTO of(CharacterInfo characterInfo) {
        return ResponseCharacterInfoDTO.builder()
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
