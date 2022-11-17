package com.nexon.maple.config.repository;

import com.nexon.maple.comment.entity.CommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentDao {
    List<CommentInfo> findByCharacterName(String characterName);
    int save(CommentInfo commentInfo);
}
