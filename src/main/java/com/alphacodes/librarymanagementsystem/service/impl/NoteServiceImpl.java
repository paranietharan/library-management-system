package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.NoteDto;
import com.alphacodes.librarymanagementsystem.Model.Note;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.repository.NoteRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Note> getAllNotes(String userId) {
        return noteRepository.findByUserId(userId);
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    public Note addNote(NoteDto note, String userId) {
        User user = userRepository.findByUserID(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        Note newNote = new Note();
        newNote.setTitle(note.getTitle());
        newNote.setContent(note.getContent());
        newNote.setUser(user);
        return noteRepository.save(newNote);
    }

    public Note updateNote(NoteDto updatedNote) {
        Note existingNote = noteRepository.findById(updatedNote.getId()).orElseThrow(() -> new IllegalArgumentException("Note not found"));
        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setContent(updatedNote.getContent());
        return noteRepository.save(existingNote);
    }

    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }
}
