package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int saveNote(Note note) {
        if(note.getNoteId()!=null){
            return noteMapper.update(note);
        } else {
            return noteMapper.insert(note);
        }
    }

    public int deleteNote(Integer noteId) {
        return noteMapper.delete(noteId);
    }

    public List<Note> getAllNotesByUserId(Integer userId) {
        return noteMapper.getAllByUserId(userId);
    }

}
