package com.nexon.maple.util.maplestoryHomepage.object;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MapleCharacter{
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
    public MapleCharacter(String image, Long rank, String rankMove, String userName, String job1, String job2, String level, String experience, Long popularity, String guildName) {
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
