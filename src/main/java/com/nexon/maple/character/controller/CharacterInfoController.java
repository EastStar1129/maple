package com.nexon.maple.character.controller;

import com.nexon.maple.character.service.CharacterInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class CharacterInfoController {
    private final CharacterInfoService characterService;

    @GetMapping("/{userName}/character")
    public ResponseEntity selectCharacter(@PathVariable("userName")String userName) {
        return ResponseEntity.ok().body(characterService.selectCharacter(userName));
    }
}
