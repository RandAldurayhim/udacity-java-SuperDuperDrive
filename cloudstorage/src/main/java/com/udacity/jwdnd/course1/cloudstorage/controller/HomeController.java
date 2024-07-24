package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.form.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.form.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    private final HomeService homeService;
    private final UserService userService;

    public HomeController(HomeService homeService, UserService userService) {
        this.homeService = homeService;
        this.userService=userService;
    }

    @GetMapping("/home")
    public String getHome(NoteForm noteForm, @ModelAttribute("newFile") FileForm newFile, CredentialForm credentialForm, Authentication authentication, Model model){
        if(authentication.getName()==null) return "login";
        Integer userId = this.userService.getUserIdByUsername(authentication.getName());
        model.addAttribute("files", this.homeService.getFilesByUserId(userId));
        model.addAttribute("creds", this.homeService.getCredentialsByUserId(userId));
        model.addAttribute("notes", this.homeService.getNotesByUserId(userId));
        return "home";
    }

}