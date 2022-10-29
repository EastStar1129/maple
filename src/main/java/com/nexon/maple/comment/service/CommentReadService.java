package com.nexon.maple.comment.service;

import com.nexon.maple.comment.dto.ResponseCommentInfo;
import com.nexon.maple.comment.entity.CommentInfo;
import com.nexon.maple.comment.repository.CommentDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentReadService {
    private final CommentDao commentDao;

    public List<ResponseCommentInfo> selectComment(String characterId) {
        List<CommentInfo> commentInfoList = commentDao.findByCharacterId(characterId);

        return ResponseCommentInfo.ofList(commentInfoList);
    }
}
