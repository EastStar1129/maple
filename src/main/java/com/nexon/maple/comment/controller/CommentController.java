package com.nexon.maple.comment.controller;

import com.nexon.maple.comment.dto.WriteCommentDTO;
import com.nexon.maple.comment.service.CommentReadService;
import com.nexon.maple.comment.service.CommentWriteService;
import com.nexon.maple.config.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@Validated
public class CommentController {
    private final CommentReadService commentReadService;
    private final CommentWriteService commentWriteService;

    @GetMapping("{characterName}/comments")
    public ResponseEntity<ResponseDTO> selectComment(@PathVariable("characterName") @NotNull String characterName) {
        return ResponseEntity.ok().body(ResponseDTO.ofSuccess("조회가 완료되었습니다.", commentReadService.selectComment(characterName)));
    }

    @PostMapping(value = "/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> saveComment(Principal principal, @RequestBody WriteCommentDTO writeComment) {
        commentWriteService.saveComment(principal.getName(), writeComment);
        return ResponseEntity.ok().body(ResponseDTO.ofSuccess("댓글이 작성되었습니다."));
    }
}
