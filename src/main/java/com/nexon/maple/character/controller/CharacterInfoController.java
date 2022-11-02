package com.nexon.maple.character.controller;

import com.nexon.maple.character.service.SelectCharacterInfoService;
import com.nexon.maple.config.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Controller
@Validated
public class CharacterInfoController {
    private final SelectCharacterInfoService selectCharacterInfoService;

    @GetMapping("/{userName}/character")
    public ResponseEntity<ResponseDTO> selectCharacter(
            @NotNull(message = "이름은 필수 값입니다.")
            @Length(min = 2, max = 12, message = "이름의 최소길이는 2자리에서 12자리입니다.")
            @PathVariable("userName") String userName) {
        var body = selectCharacterInfoService.select(userName);
        return ResponseEntity.ok().body(ResponseDTO.ofSuccess("조회가 완료되었습니다.", body));
    }
}
