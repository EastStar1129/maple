package com.nexon.maple.util.maplestoryHomepage;

import com.nexon.maple.util.maplestoryHomepage.object.MapleCharacter;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CustomMapleCharacter {
    private static final String PREFIX_URL = "https://maplestory.nexon.com/Ranking/World/Total?w=0&c=";
    private MapleCharacter mapleCharacter;

    public CustomMapleCharacter(String name) {
        init(new CustomDocument(PREFIX_URL+name));
    }

    private void init(CustomDocument customDocument) {
        Elements elements = getElements(customDocument);

        if(elements.isEmpty()) {
            return;
        }

        Element rankInfo = elements.first();
        String[] rankInfoList = findRankInfoList(rankInfo);
        String guildName = findGuildName(rankInfoList);

        setMapleCharacter(rankInfo, rankInfoList, guildName);
    }

    private void setMapleCharacter(Element rankInfo, String[] rankInfoList, String guildName) {
        String image = rankInfo.selectFirst("span.char_img > img").attr("src");
        Long rank = Long.parseLong(rankInfoList[0]);
        String rankMove = rankInfoList[1];
        String userName = rankInfoList[2];
        String job1 = rankInfoList[3];
        String job2 = rankInfoList[5];
        String level = rankInfoList[6];
        String experience = rankInfoList[7];
        Long popularity = Long.valueOf(rankInfoList[8]);
        mapleCharacter = new MapleCharacter(image, rank, rankMove, userName, job1, job2, level, experience, popularity, guildName);
    }

    private Elements getElements(CustomDocument customDocument) {
        return customDocument.getElements("tr.search_com_chk");
    }

    private String[] findRankInfoList(Element rankInfo) {
        return getRankInfo(rankInfo)
                .replaceAll(",|Lv.", "")
                .split("\\s");
    }

    private String findGuildName(String[] rankInfoList) {
        if(rankInfoList.length == 10) {
            return rankInfoList[9];
        }
        
        return null;
    }

    private String getRankInfo(Element rankInfo) {

        String rankInfoText = rankInfo.text();

        String rankInfoClassName = rankInfo.className();
        int rankInfoClassNameIndexOf = rankInfoClassName.indexOf("rank0");
        if(rankInfoClassNameIndexOf > -1) {
            rankInfoText = rankInfoClassName.substring(rankInfoClassNameIndexOf+5,rankInfoClassNameIndexOf+6) + " " + rankInfoText;
        }

        return rankInfoText;
    }

    public MapleCharacter getMapleCharacter() {
        return mapleCharacter;
    }
}
