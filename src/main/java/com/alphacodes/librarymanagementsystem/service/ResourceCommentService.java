package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.CommentDto;
import com.alphacodes.librarymanagementsystem.DTO.ResourceCommentDto;

import java.util.List;

public interface ResourceCommentService {
    ResourceCommentDto addResourceComment(Long resourceId, CommentDto CommentDto);
    List<ResourceCommentDto> getAllResourceComments(Long resourceId);
    CommentDto getResourceCommentById(Long resourceId, Long resourceCommentId);
    String deleteResourceComment(Long resourceId, Long resourceCommentId);
}
