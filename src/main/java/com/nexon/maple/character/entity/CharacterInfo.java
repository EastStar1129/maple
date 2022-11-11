package com.nexon.maple.character.entity;

import com.nexon.maple.util.maplestoryHomepage.object.MapleCharacter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CharacterInfo {
    private Long id; //기본키
    private String image; //캐릭터 이미지
    private Long characterRank; //순위
    private String rankMove; //순위변동
    private String name; //유저이름 (검색대상, index)
    private String job1; //직업군
    private String job2; //직업
    private String characterLevel; //레벨
    private String experience; //경험치
    private Long popularity; //인기도
    private String guildName; //길드이름
    private LocalDateTime createdAt; //생성일자

    @Builder
    public CharacterInfo(Long id, String image, Long characterRank, String rankMove, String name, String job1, String job2,
                         String characterLevel, String experience, Long popularity, String guildName, LocalDateTime createdAt) {
        validateNull(name);

        this.id = id;
        this.image = image;
        this.characterRank = characterRank;
        this.rankMove = rankMove;
        this.name = name;
        this.job1 = job1;
        this.job2 = job2;
        this.characterLevel = characterLevel;
        this.experience = experience;
        this.popularity = popularity;
        this.guildName = guildName;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    private void validateNull(String name) {
        Assert.notNull(name, "캐릭터 정보가 존재하지 않습니다.");
    }

    public static CharacterInfo of(String name) {
        return CharacterInfo.builder()
                .name(name)
                .build();
    }
    public static CharacterInfo of(MapleCharacter mapleCharacter) {
        return CharacterInfo.builder()
                .image(mapleCharacter.getImage())
                .characterRank(mapleCharacter.getRank())
                .rankMove(mapleCharacter.getRankMove())
                .name(mapleCharacter.getUserName())
                .job1(mapleCharacter.getJob1())
                .job2(mapleCharacter.getJob2())
                .characterLevel(mapleCharacter.getLevel())
                .experience(mapleCharacter.getExperience())
                .popularity(mapleCharacter.getPopularity())
                .guildName(mapleCharacter.getGuildName())
                .build();
    }
}
