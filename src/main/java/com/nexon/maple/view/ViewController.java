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

    @GetMapping("user/regist")
    public String register(Model model){
        model.addAttribute("terms", viewService.selectLoginTermsList());
        return "user/regist";
    }

    @GetMapping("user/login")
    public String login(){
        return "user/login";
    }


}
