package com.nexon.maple.comment.controller;

import com.nexon.maple.comment.dto.WriteComment;
import com.nexon.maple.comment.service.CommentReadService;
import com.nexon.maple.comment.service.CommentWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentReadService commentReadService;
    private final CommentWriteService commentWriteService;

    @GetMapping("{characterId}/comments")
    public ResponseEntity selectComment(@PathVariable("characterId")String characterId) {
        return ResponseEntity.ok().body(commentReadService.selectComment(characterId));
    }

    @PostMapping(value = "/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveComment(Principal principal, @RequestBody WriteComment writeComment) {
        commentWriteService.saveComment(principal, writeComment);
        return ResponseEntity.ok().build();
    }
}
