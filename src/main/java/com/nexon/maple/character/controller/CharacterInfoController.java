package com.nexon.maple.character.controller;

import com.nexon.maple.character.service.CharacterInfoReadService;
import com.nexon.maple.character.service.SelectCharacterInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Select;
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
        return ResponseEntity.ok().body(selectCharacterInfoService.select(userName));
    }
}
