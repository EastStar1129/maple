package com.nexon.maple.view;

import com.nexon.maple.config.security.jwt.JwtToken;
import com.nexon.maple.view.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class ViewController {

    private final ViewService viewService;
    private final JwtToken jwtToken;

    @GetMapping("/favicon.ico")
    @ResponseBody
    public ResponseEntity<Object> favicon() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping(value = {"/character", "/character/{userName}"})
    public String characterId(){
        return "character/character";
    }

    @GetMapping("/user/regist")
    public String register(Model model){
        model.addAttribute("terms", viewService.selectLoginTermsList());
        return "user/regist";
    }

    @GetMapping("/user/login")
    public String login(HttpServletRequest request){
        return "user/login";
    }


}
