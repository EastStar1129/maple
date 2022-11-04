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
import javax.validation.constraints.Positive;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@Validated
public class CommentController {
    private final CommentReadService commentReadService;
    private final CommentWriteService commentWriteService;

    @GetMapping("{characterId}/comments")
    public ResponseEntity<ResponseDTO> selectComment(@PathVariable("characterId") @NotNull @Positive Long characterId) {
        return ResponseEntity.ok().body(ResponseDTO.ofSuccess("조회가 완료되었습니다.", commentReadService.selectComment(characterId)));
    }

    @PostMapping(value = "/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> saveComment(Principal principal, @RequestBody WriteCommentDTO writeComment) {
        commentWriteService.saveComment(principal, writeComment);
        return ResponseEntity.ok().body(ResponseDTO.ofSuccess(""));
    }
}
