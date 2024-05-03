package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.CommentDto;
import com.alphacodes.librarymanagementsystem.service.ResourceCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/resource")
public class ResourceCommentController {
    private final ResourceCommentService resourceCommentService;

    public ResourceCommentController(ResourceCommentService resourceCommentService) {
        this.resourceCommentService = resourceCommentService;
    }

    // Add a new comment to a resource
    @PostMapping("/{rID}/comment")
    public ResponseEntity<CommentDto> addResourceComment(@PathVariable Long rID, @RequestBody CommentDto CommentDto) {
        return new ResponseEntity<>(resourceCommentService.addResourceComment(rID, CommentDto), HttpStatus.CREATED);
    }

    // Get all comments for a resource
    @GetMapping("/{rID}/comment")
    public List<CommentDto> getAllResourceComments(@PathVariable long rID) {
        return resourceCommentService.getAllResourceComments(rID);
    }

    // Get a comment by its ID
    @GetMapping("/{rID}/comment/{rcmID}")
    public ResponseEntity<CommentDto> getResourceCommentById(@PathVariable Long rcmID, @PathVariable Long rID) {
        CommentDto commentDto = resourceCommentService.getResourceCommentById(rID,rcmID);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    // Delete a comment by its ID
    @DeleteMapping("/{rID}/comment/{rcmID}")
    public ResponseEntity<String> deleteResourceComment(@PathVariable Long rcmID, @PathVariable Long rID) {
        return new ResponseEntity<>(resourceCommentService.deleteResourceComment(rID, rcmID),HttpStatus.NO_CONTENT);
    }
}
