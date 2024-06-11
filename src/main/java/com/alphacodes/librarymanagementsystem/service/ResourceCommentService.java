package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.CommentDto;

import java.util.List;

public interface ResourceCommentService {
    CommentDto addResourceComment(Long resourceId, CommentDto CommentDto);
    List<CommentDto> getAllResourceComments(Long resourceId);
    CommentDto getResourceCommentById(Long resourceId, Long resourceCommentId);
    String deleteResourceComment(Long resourceId, Long resourceCommentId);
}
