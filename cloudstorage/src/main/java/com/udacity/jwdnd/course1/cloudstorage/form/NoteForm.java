package com.udacity.jwdnd.course1.cloudstorage.form;

public class NoteForm {
    private String noteTitle;
    private String noteDescription;
    private Integer noteId;

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public NoteForm() {
    }

    public NoteForm(String noteTitle, String noteDescription, Integer noteId) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}
