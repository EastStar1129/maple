package com.nexon.maple.character.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ResponseCharacterInfo {
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
    public ResponseCharacterInfo(Long id, String image, Long rank, String rankMove, String userName, String job1, String job2, String level, String experience, Long popularity, String guildName) {
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
}
