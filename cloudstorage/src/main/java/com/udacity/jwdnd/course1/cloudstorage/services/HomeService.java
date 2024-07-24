package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {
    private final NoteService noteService;
    private final FileService fileService;
    private final CredentialService credentialService;

    public HomeService(NoteService noteService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    public List<Note> getNotesByUserId(Integer userId){
        return this.noteService.getAllNotesByUserId(userId);
    }

    public List<Credential> getCredentialsByUserId(Integer userId){
        return this.credentialService.getAllCredentialsByUserId(userId);
    }

    public List<File> getFilesByUserId(Integer userId){
        return this.fileService.getFilesByUserId(userId);
    }

}
