package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.Constants;
import com.udacity.jwdnd.course1.cloudstorage.form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService= userService;
    }
    @PostMapping("/addNote")
    public String addNote(NoteForm noteForm, Authentication authentication, Model model){
        try {
            Integer userId = this.userService.getUserIdByUsername(authentication.getName());            Note note = new Note(noteForm.getNoteId()!=null?noteForm.getNoteId():null, noteForm.getNoteTitle(), noteForm.getNoteDescription(),userId);
            Integer noteId =this.noteService.saveNote(note);
            noteForm.setNoteId(noteId);
            model.addAttribute("success", true);
            model.addAttribute("message", Constants.SUCCESS_MESSAGE);
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", Constants.GENERAL_ERROR_MESSAGE);
        }
        return "result";
    }

    @GetMapping("/deleteNote/{id}")
    public String deleteNote(@PathVariable("id") Integer id, Model model) {
        try {
            this.noteService.deleteNote(id);
            model.addAttribute("success", true);
            model.addAttribute("message", Constants.SUCCESS_MESSAGE);
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", Constants.GENERAL_ERROR_MESSAGE);
        }
        return "result";
    }
}
