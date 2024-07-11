package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.NoteDto;
import com.alphacodes.librarymanagementsystem.Model.Note;
import com.alphacodes.librarymanagementsystem.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/{userId}")
    public List<Note> getAllNotes(@PathVariable String userId) {
        return noteService.getAllNotes(userId);
    }

    @GetMapping("/note/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }

    @PostMapping("/{userId}")
    public Note addNote(@RequestBody NoteDto note, @PathVariable String userId) {
        return noteService.addNote(note, userId);
    }

    @PutMapping("/")
    public Note updateNote(@RequestBody NoteDto note) {
        return noteService.updateNote(note);
    }

    @DeleteMapping("/{id}")
    public void deleteNoteById(@PathVariable Long id) {
        noteService.deleteNoteById(id);
    }
}
