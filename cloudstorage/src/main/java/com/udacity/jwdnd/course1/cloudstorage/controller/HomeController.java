package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.form.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.form.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class HomeController {
    private final HomeService homeService;
    private final EncryptionService encryptionService;
    private final UserService userService;

    private boolean success = false;
    private boolean error = false;
    public HomeController(HomeService homeService,EncryptionService encryptionService, UserService userService) {
        this.homeService = homeService;
        this.encryptionService=encryptionService;
        this.userService=userService;
    }

    @GetMapping("/home")
    public String getHome(NoteForm noteForm, @ModelAttribute("newFile") FileForm newFile, CredentialForm credentialForm, Authentication authentication, Model model){
        Integer userId = this.homeService.getUserIdByUsername(authentication.getName());
        model.addAttribute("files", this.homeService.getFilesByUserId(userId));
        model.addAttribute("creds", this.homeService.getCredentialsByUserId(userId));
        model.addAttribute("notes", this.homeService.getNotesByUserId(userId));
        return "home";
    }

    @PostMapping("/addFile")
    public String addFile( @ModelAttribute("newFile") FileForm newFile, Authentication authentication, Model model) {
        try {
            String fileName = newFile.getFile().getOriginalFilename();
            String contentType = newFile.getFile().getContentType();
            long fileSize = newFile.getFile().getSize();
            Integer userId = this.homeService.getUserIdByUsername(authentication.getName());

            File file = new File(null,fileName, contentType, fileSize, userId, newFile.getFile().getBytes());
            this.homeService.addFile(file);
            success = true;
        } catch (Exception e) {
            error = true;
        }
        if (success) {
            model.addAttribute("success", success);
        } else {
            model.addAttribute("error", error);
        }
        return "result";
    }

    @PostMapping("/addNote")
    public String addNote(NoteForm noteForm, Authentication authentication, Model model){
        try {
            Integer userId = this.homeService.getUserIdByUsername(authentication.getName());
            Note note = new Note(noteForm.getNoteId()!=null?noteForm.getNoteId():null, noteForm.getNoteTitle(), noteForm.getNoteDescription(),userId);
            Integer noteId =this.homeService.saveNote(note);
            noteForm.setNoteId(noteId);
            success = true;
        } catch (Exception e) {
            error = true;
        }
        if (success) {
            model.addAttribute("success", success);
        } else {
            model.addAttribute("error", error);
        }
        return "result";
    }

    @PostMapping("/saveCred")
    public String addCred(CredentialForm credentialForm, Authentication authentication, Model model)  {
        try {
        Integer userId = this.homeService.getUserIdByUsername(authentication.getName());
        byte[] keyBytes = SecureRandom.getInstance("SHA1PRNG").generateSeed(16); // Generate 16 bytes for AES key
        String serverKey = Base64.getEncoder().encodeToString(keyBytes);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), serverKey);
        Credential credential = new Credential(credentialForm.getCredentialId()!=null?credentialForm.getCredentialId():null,credentialForm.getUrl(),
                credentialForm.getUsername(),serverKey, encryptedPassword,userId);
        Integer credId=this.homeService.saveCred(credential);
        credentialForm.setCredentialId(credId);
            success = true;
        } catch (Exception e) {
            error = true;
        }
        if (success) {
            model.addAttribute("success", success);
        } else {
            model.addAttribute("error", error);
        }
        return "result";
    }

    @GetMapping("/viewFile/{id}")
    public void viewFile(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        File file = this.homeService.getFileById(id);

        if (file != null) {
            String fileName = file.getFileName();
            String contentType = file.getContentType();
            byte[] fileData = file.getFileData();

            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentLength(fileData.length);

            OutputStream out = response.getOutputStream();
            out.write(fileData);
            out.flush();
            out.close();
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
        }
    }

    @GetMapping("/deleteFile/{id}")
    public String deleteFile( @PathVariable("id") Integer id, Model model) {
        try {
        this.homeService.deleteFile(id);
            success = true;
        } catch (Exception e) {
            error = true;
        }
        if (success) {
            model.addAttribute("success", success);
        } else {
            model.addAttribute("error", error);
        }
        return "result";
    }
    @GetMapping("/deleteNote/{id}")
    public String deleteNote(@PathVariable("id") Integer id, Model model) {
        try {
        this.homeService.deleteNote(id);
            success = true;
        } catch (Exception e) {
            error = true;
        }
        if (success) {
            model.addAttribute("success", success);
        } else {
            model.addAttribute("error", error);
        }
        return "result";
    }
    @GetMapping("/deleteCred/{id}")
    public String deleteCred( @PathVariable("id") Integer id, Model model) {
        try {
        this.homeService.deleteCred(id);
            success = true;
        } catch (Exception e) {
            error = true;
        }
        if (success) {
            model.addAttribute("success", success);
        } else {
            model.addAttribute("error", error);
        }
        return "result";
    }

    @GetMapping(value = "/getDecryptedPassword/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getDecryptedPassword(@PathVariable String id) {
        Integer credentialid = Integer.parseInt(id);
        Credential credential = this.homeService.getCredById(credentialid);
        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(),credential.getKey());
        String password = "{ \"password\": \"" + decryptedPassword + "\"}";
        return new ResponseEntity<String>(password, HttpStatus.OK);
    }



}