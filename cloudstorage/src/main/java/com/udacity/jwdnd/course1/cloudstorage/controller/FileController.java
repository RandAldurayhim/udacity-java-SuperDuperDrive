package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.Constants;
import com.udacity.jwdnd.course1.cloudstorage.form.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


import static com.udacity.jwdnd.course1.cloudstorage.Constants.FILE_NOT_FOUND_ERROR;

@Controller
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService,UserService userService) {
        this.fileService = fileService;
        this.userService=userService;
    }

    @PostMapping("/addFile")
    public String addFile(@ModelAttribute("newFile") FileForm newFile, Authentication authentication, Model model) {
        try {
            MultipartFile multipartFile = newFile.getFile();
            String fileName = newFile.getFile().getOriginalFilename();
            String contentType = newFile.getFile().getContentType();
            long fileSize = newFile.getFile().getSize();
            Integer userId = this.userService.getUserIdByUsername(authentication.getName());

            // Check if file is empty
            if (multipartFile.isEmpty()) {
                model.addAttribute("success", false);
                model.addAttribute("message", Constants.EMPTY_FILE_ERROR);
                return "result";
            }

            // Check if file already exits for the same user
            if (fileService.countByFilenameAndUserId(fileName, userId) > 0) {
                model.addAttribute("success", false);
                model.addAttribute("message", Constants.FILE_ALREADY_EXISTS_ERROR);
                return "result";
            }


            File file = new File(null, fileName, contentType, fileSize, userId, newFile.getFile().getBytes());
            this.fileService.saveFile(file);
            model.addAttribute("success", true);
            model.addAttribute("message", Constants.SUCCESS_MESSAGE);

        }
        catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", Constants.GENERAL_ERROR_MESSAGE);
        }
        return "result";
    }

    @GetMapping("/viewFile/{id}")
    public void viewFile(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        File file = this.fileService.getFileById(id);

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
            response.sendError(HttpServletResponse.SC_NOT_FOUND, FILE_NOT_FOUND_ERROR);
        }
    }

    @GetMapping("/deleteFile/{id}")
    public String deleteFile( @PathVariable("id") Integer id, Model model) {
        try {
            this.fileService.deleteFile(id);
            model.addAttribute("success", true);
            model.addAttribute("message", Constants.SUCCESS_MESSAGE);
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", Constants.GENERAL_ERROR_MESSAGE);
        }
        return "result";
    }
}
