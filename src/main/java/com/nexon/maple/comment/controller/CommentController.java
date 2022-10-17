package com.nexon.maple.comment.controller;

import com.nexon.maple.comment.dto.WriteComment;
import com.nexon.maple.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("{characterId}/comments")
    public ResponseEntity selectComment(@PathVariable("characterId")String characterId) {
        return ResponseEntity.ok().body(commentService.selectComment(characterId));
    }

    @PutMapping(value = "/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveComment(@RequestBody WriteComment writeComment) {
        commentService.saveComment(writeComment);
        return ResponseEntity.ok().build();
    }
}
