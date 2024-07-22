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
    private final UserService userService;

    public HomeService(NoteService noteService, FileService fileService, CredentialService credentialService,UserService userService) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.userService=userService;
    }

    public Integer getUserIdByUsername( String username){
        return this.userService.getUser(username).getUserId();
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

    public void addFile(File file){
        this.fileService.saveFile(file);
    }

    public Integer saveNote(Note note){
        return this.noteService.saveNote(note);
    }

    public Integer saveCred(Credential credential){
        return this.credentialService.saveCredential(credential);
    }

    public void deleteNote(Integer noteId){
        this.noteService.deleteNote(noteId);
    }

    public void deleteCred(Integer credId){
        this.credentialService.deleteCredential(credId);
    }

    public void deleteFile(Integer fileId){
        this.fileService.deleteFile(fileId);
    }

    public File getFileById(Integer fileId){
        return this.fileService.getFileById(fileId);
    }

    public Credential getCredById(Integer id){
        return this.credentialService.getCredById(id);
    }

}
