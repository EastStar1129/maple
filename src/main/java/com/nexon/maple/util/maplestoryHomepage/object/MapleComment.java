package com.nexon.maple.util.maplestoryHomepage.object;

public record MapleComment (
    String commentSn,
    String parentCommentSn,
    String commentWriterName,
    String emoticonNo,
    String imageSn,
    String commentContent,
    String worldImg
){
    public boolean equalsCommentContent(String otpNumber) {
        return commentContent.equals(otpNumber);
    }
}