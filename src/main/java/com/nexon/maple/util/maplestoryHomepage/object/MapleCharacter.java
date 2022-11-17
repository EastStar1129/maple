package com.nexon.maple.util.maplestoryHomepage.object;

public record MapleCharacter (
    String image,
    Long rank,
    String rankMove,
    String userName,
    String job1,
    String job2,
    String level,
    String experience,
    Long popularity,
    String guildName
){}
