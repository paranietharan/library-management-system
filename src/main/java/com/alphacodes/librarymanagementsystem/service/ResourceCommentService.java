package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.ResourceCommentDto;

import java.util.List;

public interface ResourceCommentService {
    ResourceCommentDto addResourceComment(long rID, ResourceCommentDto resourceCommentDto);
    List<ResourceCommentDto> getAllResourceComments(Long rID);
    ResourceCommentDto getResourceCommentById(Long rID, Long rcmID);
    String deleteResourceComment(Long rID, Long rcmID);
}
