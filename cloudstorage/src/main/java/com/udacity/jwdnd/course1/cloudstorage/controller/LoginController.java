package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping()
    public String loginView(Model model) {
        Boolean signupSuccess = (Boolean) model.asMap().get("signupSuccess");
        if(Boolean.TRUE.equals(signupSuccess))
         model.addAttribute("signupSuccess", true);

        return "login";
    }
}