package com.nexon.maple.comment.service;

import com.nexon.maple.comment.dto.WriteComment;
import com.nexon.maple.comment.entity.CommentInfo;
import com.nexon.maple.comment.repository.CommentDao;
import com.nexon.maple.userInfo.dto.ResponseUserInfo;
import com.nexon.maple.userInfo.service.UserInfoReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;

@RequiredArgsConstructor
@Service
public class CommentWriteService {
    private final CommentDao commentDao;
    private final UserInfoReadService userInfoReadService;

    public void saveComment(Principal principal, WriteComment writeComment) {
        ResponseUserInfo userInfo = userInfoReadService.selectUserInfo(principal.getName());

        CommentInfo commentInfo = CommentInfo.builder()
                .characterId(writeComment.characterId())
                .userId(userInfo.id())
                .comment(writeComment.comment())
                .build();

        Assert.isTrue(commentDao.save(commentInfo) == 1, "댓글이 저장되지 않았습니다.");
    }

}
