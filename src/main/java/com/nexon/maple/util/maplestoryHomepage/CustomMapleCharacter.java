package com.nexon.maple.util.maplestoryHomepage;

import com.nexon.maple.util.maplestoryHomepage.object.MapleCharacter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CustomMapleCharacter extends CustomDocument{
    private static final String PREFIX_URL = "https://maplestory.nexon.com/Ranking/World/Total?w=0&c=";
    private final String name;

    public CustomMapleCharacter(String name) {
        this.name = name;
    }

    @Override
    public MapleCharacter build() throws IOException {
        Document document = connect(PREFIX_URL+name);
        Elements elements = document.select("tr.search_com_chk");
        return ofMapleCharacter(elements);
    }

    /*
        goal : 검색된 정보를 가공하여 MapleCharacter 타입으로 반환
        param : elements (검색된 정보)
        return : MapleCharacter (메이플 케릭터 정보)
    */
    private MapleCharacter ofMapleCharacter(Elements elements) {
        if(elements.isEmpty()) {
            return null;
        }

        Element rankInfo = elements.first();
        String image = rankInfo.selectFirst("span.char_img > img").attr("src");

        String[] rankInfoList = parseRankInfoList(rankInfo);
        Long rank = Long.parseLong(rankInfoList[0]);
        String rankMove = rankInfoList[1];
        String userName = rankInfoList[2];
        String job1 = rankInfoList[3];
        String job2 = rankInfoList[5];
        String level = rankInfoList[6];
        String experience = rankInfoList[7];
        Long popularity = Long.valueOf(rankInfoList[8]);

        String guildName = parseGuildName(rankInfoList);

        return new MapleCharacter(image, rank, rankMove, userName, job1, job2, level, experience, popularity, guildName);
    }

    /*
        goal : guild name 추출
     */
    private String parseGuildName(String[] rankInfoList) {
        if (rankInfoList.length == 10) {
            return rankInfoList[9];
        }
        return null;
    }

    /*
        goal : String 정보를 String []으로 변환
     */
    private String[] parseRankInfoList(Element rankInfo) {
        return parseRankText(rankInfo)
                .replaceAll(",|Lv.", "")
                .split("\\s");
    }

    /*
        goal : String 정보를 String []으로 변환
     */
    private String parseRankText(Element rankInfo) {
        String rankInfoClassName = rankInfo.className();
        int rankInfoClassNameIndexOf = rankInfoClassName.indexOf("rank0");

        return rankInfoClassNameIndexOf > -1 ?
                rankInfoClassName.substring(rankInfoClassNameIndexOf+5, rankInfoClassNameIndexOf+6) + " " + rankInfo.text() :
                rankInfo.text();
    }
}
