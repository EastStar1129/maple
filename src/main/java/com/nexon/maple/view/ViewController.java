package com.nexon.maple.view;

import com.nexon.maple.view.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ViewController {

    private final ViewService viewService;

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping(value = {"/character", "/character/{userName}"})
    public String character(){
        return "character/character";
    }

    @GetMapping("/user/regist")
    public String register(Model model){
        model.addAttribute("terms", viewService.selectLoginTermsList());
        return "user/regist";
    }

    @GetMapping("/user/login")
    public String login(){
        return "user/login";
    }


}
