package com.nexon.maple.comment.service;

import com.nexon.maple.comment.dto.ResponseCommentInfo;
import com.nexon.maple.comment.dto.WriteComment;
import com.nexon.maple.comment.entity.CommentInfo;
import com.nexon.maple.comment.repository.CommentDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
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

    public List<ResponseCommentInfo> selectComment(String characterId) {
        List<CommentInfo> commentInfoList = commentDao.findByCharacterId(characterId);

        List<ResponseCommentInfo> list = new ArrayList<>();
        for(CommentInfo commentInfo: commentInfoList) {
            list.add(ResponseCommentInfo.builder()
                    .id(commentInfo.getId())
                    .userId(commentInfo.getUserId())
                    .comment(commentInfo.getComment())
                    .createdAt(commentInfo.getCreatedAt())
                    .build());
        }

        return list;
    }
}
