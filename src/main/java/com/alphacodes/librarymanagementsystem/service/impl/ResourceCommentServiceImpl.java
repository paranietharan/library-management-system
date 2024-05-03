package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.CommentDto;
import com.alphacodes.librarymanagementsystem.Model.ResourceComment;
import com.alphacodes.librarymanagementsystem.repository.ResourceCommentRepository;
import com.alphacodes.librarymanagementsystem.repository.ResourceRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.ResourceCommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceCommentServiceImpl implements ResourceCommentService{

    private final ResourceCommentRepository resourceCommentRepository;
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;
    public ResourceCommentServiceImpl(ResourceCommentRepository resourceCommentRepository, ResourceRepository resourceRepository, UserRepository userRepository) {
        this.resourceCommentRepository = resourceCommentRepository;
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentDto addResourceComment(Long rID, CommentDto commentDto) {
        ResourceComment resourceComment1 = convertToResourceComment(commentDto);
        resourceComment1.setBook(resourceRepository.findById(rID).orElseThrow(
                () -> new RuntimeException("Resource not found with id " + rID)));


        ResourceComment newResourceComment = resourceCommentRepository.save(resourceComment1);
        return convertToCommentDto(newResourceComment);
    }

    @Override
    public List<CommentDto> getAllResourceComments(Long rID) {
        List<ResourceComment> resourceComments = resourceCommentRepository.findByBook(resourceRepository.findById(rID).orElseThrow(
                () -> new RuntimeException("Resource not found with id " + rID))
        );
        return resourceComments.stream().map(this::convertToCommentDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getResourceCommentById(Long rID, Long rcmID) {
        ResourceComment resourceComment = resourceCommentRepository.findById(rcmID).orElseThrow(
                () -> new RuntimeException("Resource Comment not found with id " + rcmID));
        return convertToCommentDto(resourceComment);
    }

    @Override
    public String deleteResourceComment(Long rID, Long rcmID) {
        ResourceComment resourceComment = resourceCommentRepository.findById(rcmID).orElseThrow(
                () -> new RuntimeException("Resource Comment not found with id " + rcmID));
        resourceCommentRepository.delete(resourceComment);
        return "Resource Comment deleted Successfully";
    }

    private ResourceComment convertToResourceComment(CommentDto commentDto){
        ResourceComment resourceComment = new ResourceComment();
        resourceComment.setMember(userRepository.findByUserID(commentDto.getUserID()).orElse(null));
        resourceComment.setComment(commentDto.getComment());
        return resourceComment;
    }

    private CommentDto convertToCommentDto(ResourceComment resourceComment){
        CommentDto CommentDto = new CommentDto();
        CommentDto.setUserID(resourceComment.getMember().getUserID());
        CommentDto.setComment(resourceComment.getComment());
        return CommentDto;
    }

}
