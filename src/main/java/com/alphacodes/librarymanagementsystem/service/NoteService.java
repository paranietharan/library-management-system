package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.NoteDto;
import com.alphacodes.librarymanagementsystem.Model.Note;

import java.util.List;

public interface NoteService {
    public List<Note> getAllNotes(String userId);
    Note getNoteById(Long id);
    Note addNote(NoteDto note, String userId);
    Note updateNote(NoteDto updatedNote);
    public void deleteNoteById(Long id);
}
