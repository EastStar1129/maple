package com.nexon.maple.comment.controller;

import com.nexon.maple.comment.dto.WriteComment;
import com.nexon.maple.comment.service.CommentReadService;
import com.nexon.maple.comment.service.CommentWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentReadService commentReadService;
    private final CommentWriteService commentWriteService;

    @GetMapping("{characterId}/comments")
    public ResponseEntity selectComment(@PathVariable("characterId")String characterId) {
        return ResponseEntity.ok().body(commentReadService.selectComment(characterId));
    }

    @PutMapping(value = "/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveComment(@RequestBody WriteComment writeComment) {
        commentWriteService.saveComment(writeComment);
        return ResponseEntity.ok().build();
    }
}
