package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.CommentDto;

import java.util.List;

public interface ResourceCommentService {
    CommentDto addResourceComment(Long rID, CommentDto CommentDto);
    List<CommentDto> getAllResourceComments(Long rID);
    CommentDto getResourceCommentById(Long rID, Long rcmID);
    String deleteResourceComment(Long rID, Long rcmID);
}
