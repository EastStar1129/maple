package com.nexon.maple.comment.service;

import com.nexon.maple.comment.dto.WriteComment;
import com.nexon.maple.comment.entity.CommentInfo;
import com.nexon.maple.comment.repository.CommentDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class CommentWriteService {
    private final CommentDao commentDao;
    public void saveComment(WriteComment writeComment) {
        /*
         * TODO userId 1L = 로그인 사용자의 ID를 넣어야된다
         *
         * */
        CommentInfo commentInfo = CommentInfo.builder()
                .characterId(writeComment.characterId())
                .userId(1L)
                .comment(writeComment.comment())
                .build();

        Assert.isTrue(commentDao.save(commentInfo) == 1, "댓글이 저장되지 않았습니다.");
    }

}
