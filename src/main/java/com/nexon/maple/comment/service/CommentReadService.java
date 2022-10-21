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
public class CommentReadService {
    private final CommentDao commentDao;

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
