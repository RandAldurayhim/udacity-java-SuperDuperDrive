package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.Constants;
import com.udacity.jwdnd.course1.cloudstorage.form.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class CredentialController {
    private final EncryptionService encryptionService;
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(EncryptionService encryptionService, CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.encryptionService=encryptionService;
        this.userService=userService;
    }
    @PostMapping("/saveCred")
    public String addCred(CredentialForm credentialForm, Authentication authentication, Model model)  {
        try {
            Integer userId = this.userService.getUserIdByUsername(authentication.getName());
            byte[] keyBytes = SecureRandom.getInstance("SHA1PRNG").generateSeed(16); // Generate 16 bytes for AES key
            String serverKey = Base64.getEncoder().encodeToString(keyBytes);
            String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), serverKey);
            Credential credential = new Credential(credentialForm.getCredentialId()!=null?credentialForm.getCredentialId():null,credentialForm.getUrl(),
                    credentialForm.getUsername(),serverKey, encryptedPassword,userId);
            Integer credId=this.credentialService.saveCredential(credential);
            credentialForm.setCredentialId(credId);
            model.addAttribute("success", true);
            model.addAttribute("message", Constants.SUCCESS_MESSAGE);
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", Constants.GENERAL_ERROR_MESSAGE);
        }
        return "result";
    }

    @GetMapping("/deleteCred/{id}")
    public String deleteCred(@PathVariable("id") Integer id, Model model) {
        try {
            this.credentialService.deleteCredential(id);
            model.addAttribute("success", true);
            model.addAttribute("message", Constants.SUCCESS_MESSAGE);
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", Constants.GENERAL_ERROR_MESSAGE);
        }
        return "result";
    }

    @GetMapping(value = "/getDecryptedPassword/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getDecryptedPassword(@PathVariable String id) {
        Integer credentialId = Integer.parseInt(id);
        Credential credential = this.credentialService.getCredById(credentialId);
        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(),credential.getKey());
        String password = "{ \"password\": \"" + decryptedPassword + "\"}";
        return new ResponseEntity<>(password, HttpStatus.OK);
    }

}
