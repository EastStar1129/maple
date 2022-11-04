package com.nexon.maple.comment.repository;

import com.nexon.maple.comment.entity.CommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentDao {
    List<CommentInfo> findByCharacterId(Long characterId);
    int save(CommentInfo commentInfo);
}
