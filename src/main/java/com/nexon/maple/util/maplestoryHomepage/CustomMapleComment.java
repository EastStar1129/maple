package com.nexon.maple.util.maplestoryHomepage;

import com.nexon.maple.util.maplestoryHomepage.object.MapleComment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class CustomMapleComment extends CustomDocument {
    private static final String PREFIX_URL = "https://maplestory.nexon.com/Community/Free/326252/comment?page=";
    private final String name;
    private final Long pageNumber;

    //메이플 게시판 댓글 가져오기
    public CustomMapleComment(String name, Long pageNumber) {
        this.name = name;
        this.pageNumber = pageNumber;
    }

    @Override
    public MapleComment build() throws IOException {
        String url = PREFIX_URL + pageNumber;
        HashMap headers = getHeaders();

        Document document = connect(url, headers);
        Element element = parseComment(document);
        return ofMapleComment(element);
    }

    private Element parseComment(Document document) {
        Elements commentElements = document.select("div.reply");

        return commentElements
                .stream()
                .filter(element -> commentFilter(element))
                .findFirst()
                .orElse(null);
    }

    private MapleComment ofMapleComment(Element commentElement) {
        if(Objects.isNull(commentElement)) {
            return null;
        }

        String commentSn = commentElement.selectFirst("input[name=comment_sn]").attr("value");
        String parentCommentSn = commentElement.selectFirst("input[name=parent_comment_sn]").attr("value");
        String commentWriterName = commentElement.selectFirst("input[name=comment_writer_name]").attr("value");
        String emoticonNo = commentElement.selectFirst("input[name=emoticon_no]").attr("value");
        String imageSn = commentElement.selectFirst("input[name=image_sn]").attr("value");
        String commentContent = commentElement.selectFirst("textarea[name=comment_content]").text();
        String worldImg = commentElement.selectFirst("img[alt=캐릭터 아이콘]").attr("src");
        return new MapleComment(commentSn, parentCommentSn, commentWriterName, emoticonNo, imageSn, commentContent, worldImg);
    }

    private boolean commentFilter(Element commentElement) {
        return commentElement
                .selectFirst("input[name=comment_writer_name]")
                .attr("value")
                .equals(name);
    }

    private HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "*/*");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.put("Connection", "keep-alive");
        headers.put("Cookie", "PCID=16394846695239032453550; _gcl_au=1.1.963024700.1639484670; NXGID=455962DDC3018A9C439E135F504C4542; _fbp=fb.1.1639484670187.184814106; _ga_NEHB476HQQ=GS1.1.1639798427.1.1.1639798640.0; _hjSessionUser_1327448=eyJpZCI6Ijc2ODFkYjYwLTQ3YzYtNTUwZS05YTc0LTZkNjhlNGZmYjVmYiIsImNyZWF0ZWQiOjE2Mzk0ODQ2NzAxNTQsImV4aXN0aW5nIjp0cnVlfQ==; _ga_85SWJGCW5C=GS1.1.1642865580.3.0.1642865580.0; NXPID=87E1F4DB9DF9895DB438C4A1492BFE49; EGCMP=; EGC2=; EGCGM=; A2SK=act:16470132293251809853; _gid=GA1.2.965749465.1647013229; isCafe=false; NLWGID=0; __RequestVerificationToken=FxQBwM-YLIYu8Rgu-zgWn17SmoZSNjRVp7VikwyZhrX8OItFDFP10HdjAtYrYrS7LqsakJk_7OPVrO0KjiqHR_L2U_c1; NXABP=; GGAN=0; NXMP=; _hjSession_1327448=eyJpZCI6ImQyYTI2MjViLTBkNjgtNDlhNy1hMTA0LTFjNDQyNmEyNzUzYyIsImNyZWF0ZWQiOjE2NDcwNzExMjEyNDMsImluU2FtcGxlIjpmYWxzZX0=; _hjAbsoluteSessionInProgress=0; M_JL=1; IL=; NXCH=; IM=0; RDB=; NPP=; ENC=; EGC=; MSGENC=; MSGENCT=; EGCMSCM=; NXLW=SID=54A7494FA652C0C163FAB00648995B38&PTC=https:&DOM=maplestory.nexon.com&ID=&CP=; _gat_ADTeamTracker=1; _gat_gtag_UA_34233427_4=1; gnbLogo=null; _gat_UA-116900215-1=1; _gat_UA-136873854-1=1; cto_bundle=5Ecz6V9IM0RxenlWTFNPeGxLd2RzajNTTlY3NEYlMkZtZTVmWm5wa2JKViUyQiUyQlFHNnozVmtIV1VwQndwS3BlRiUyQnk2d2lRbUFhQXNrTjBPdlVVT2YzNFlUSU9EVTVCbGZZVHFzaDZBM1lyc3hNUUVpWjAxUGlkJTJGWWExV3JEN243bFA0Nm9mbE42dHZXQWlINndrTnR5dXpvUjhmZVl3JTNEJTNE; _ga_12M3M6FCC2=GS1.1.1647071120.29.1.1647074369.0; _ga=GA1.2.1141764022.1639484670");
        headers.put("Host", "maplestory.nexon.com");
        headers.put("Referer", "https://maplestory.nexon.com/Home/Main");
        headers.put("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"99\", \"Google Chrome\";v=\"99\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "");
        headers.put("Sec-Fetch-Dest", "empty");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("Sec-Fetch-Site", "same-origin");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }
}
