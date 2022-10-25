package com.nexon.maple.character.controller;

import com.nexon.maple.character.service.SelectCharacterInfoService;
import com.nexon.maple.config.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class CharacterInfoController {
    private final SelectCharacterInfoService selectCharacterInfoService;

    @GetMapping("/{userName}/character")
    public ResponseEntity selectCharacter(@PathVariable("userName")String userName) {
        var body = selectCharacterInfoService.select(userName);
        return ResponseEntity.ok().body(ResponseDTO.ofSuccess("조회가 완료되었습니다.", body));
    }
}
