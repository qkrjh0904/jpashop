package com.jeongho.jpashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    @GetMapping("/")
    public String index(Model model, Principal principal){
        String message = "Index 페이지 입니다.";
        if(principal != null) {
            message += principal.getName() + "님.";
        }
        model.addAttribute("message", message);
        return "index";
    }

    @GetMapping("/info")
    public String info(Model model){
        model.addAttribute("message", "Info 페이지 입니다.");
        return "info";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal){
        model.addAttribute("message", "Admin 페이지 입니다.");
        model.addAttribute("userName", "안녕하세요 "+principal.getName()+" 님.");
        return "admin";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal){
        model.addAttribute("message", "Dashboard 페이지 입니다.");
        model.addAttribute("userName", "안녕하세요 "+principal.getName()+" 님.");
        return "dashboard";
    }
}
