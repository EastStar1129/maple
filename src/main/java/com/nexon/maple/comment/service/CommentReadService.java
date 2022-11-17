package com.nexon.maple.comment.service;

import com.nexon.maple.comment.dto.ResponseCommentInfoDTO;
import com.nexon.maple.comment.entity.CommentInfo;
import com.nexon.maple.config.repository.CommentDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentReadService {
    private final CommentDao commentDao;

    public List<ResponseCommentInfoDTO> selectComment(String characterName) {
        List<CommentInfo> commentInfoList = commentDao.findByCharacterName(characterName);

        return ResponseCommentInfoDTO.ofList(commentInfoList);
    }
}
