package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.CommentDto;
import com.alphacodes.librarymanagementsystem.DTO.ResourceCommentDto;
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
    @PostMapping("/{resourceId}/comment")
    public ResponseEntity<ResourceCommentDto> addResourceComment(
            @PathVariable Long resourceId,
            @RequestBody CommentDto commentDto
    ) {
        return new ResponseEntity<>(resourceCommentService.addResourceComment(resourceId, commentDto), HttpStatus.CREATED);
    }

    // Get all comments for a resource
    @GetMapping("/{resourceId}/comment")
    public List<ResourceCommentDto> getAllResourceComments(@PathVariable long resourceId) {
        return resourceCommentService.getAllResourceComments(resourceId);
    }

    // Get a comment by its ID
    @GetMapping("/{resourceId}/comment/{resourceCommentId}")
    public ResponseEntity<CommentDto> getResourceCommentById(@PathVariable Long resourceCommentId, @PathVariable Long resourceId) {
        CommentDto commentDto = resourceCommentService.getResourceCommentById(resourceId,resourceCommentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    // Delete a comment by its ID
    @DeleteMapping("/{resourceId}/comment/{resourceCommentId}")
    public ResponseEntity<String> deleteResourceComment(@PathVariable Long resourceCommentId, @PathVariable Long resourceId) {
        return new ResponseEntity<>(resourceCommentService.deleteResourceComment(resourceId, resourceCommentId),HttpStatus.NO_CONTENT);
    }
}
