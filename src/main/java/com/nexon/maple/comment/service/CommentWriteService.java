package com.nexon.maple.comment.service;

import com.nexon.maple.comment.dto.WriteCommentDTO;
import com.nexon.maple.comment.entity.CommentInfo;
import com.nexon.maple.config.repository.CommentDao;
import com.nexon.maple.userInfo.dto.ResponseUserInfoDTO;
import com.nexon.maple.userInfo.service.UserInfoReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class CommentWriteService {
    private final CommentDao commentDao;
    private final UserInfoReadService userInfoReadService;

    public CommentInfo saveComment(String userName, WriteCommentDTO writeComment) {
        ResponseUserInfoDTO userInfo = userInfoReadService.selectUserInfo(userName);
        CommentInfo commentInfo = CommentInfo.of(writeComment, userInfo);
        commentDao.save(commentInfo);
        Assert.notNull(commentInfo.getId(), "댓글이 저장되지 않았습니다.");
        return commentInfo;
    }
}
