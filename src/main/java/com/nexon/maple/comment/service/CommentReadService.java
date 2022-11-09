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

    public List<ResponseCommentInfoDTO> selectComment(Long characterId) {
        List<CommentInfo> commentInfoList = commentDao.findByCharacterId(characterId);

        return ResponseCommentInfoDTO.ofList(commentInfoList);
    }
}
