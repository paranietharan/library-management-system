package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.ResourceCommentDto;
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
    public ResourceCommentDto addResourceComment(int userID,Long rID, ResourceCommentDto resourceCommentDto) {
        ResourceComment resourceComment1 = convertToResourceComment(resourceCommentDto);
        resourceComment1.setBook(resourceRepository.findById(rID).orElseThrow(
                () -> new RuntimeException("Resource not found with id " + rID)));
        resourceComment1.setMember(userRepository.findByUserID(userID).orElse(null));

        ResourceComment newResourceComment = resourceCommentRepository.save(resourceComment1);
        return convertToResourceCommentDto(newResourceComment);
    }

    @Override
    public List<ResourceCommentDto> getAllResourceComments(Long rID) {
        List<ResourceComment> resourceComments = resourceCommentRepository.findByBook(resourceRepository.findById(rID).orElseThrow(
                () -> new RuntimeException("Resource not found with id " + rID))
        );
        return resourceComments.stream().map(this::convertToResourceCommentDto).collect(Collectors.toList());
    }

    @Override
    public ResourceCommentDto getResourceCommentById(Long rID, Long rcmID) {
        ResourceComment resourceComment = resourceCommentRepository.findById(rcmID).orElseThrow(
                () -> new RuntimeException("Resource Comment not found with id " + rcmID));
        return convertToResourceCommentDto(resourceComment);
    }

    @Override
    public String deleteResourceComment(Long rID, Long rcmID) {
        ResourceComment resourceComment = resourceCommentRepository.findById(rcmID).orElseThrow(
                () -> new RuntimeException("Resource Comment not found with id " + rcmID));
        resourceCommentRepository.delete(resourceComment);
        return "Resource Comment deleted Successfully";
    }

    private ResourceComment convertToResourceComment(ResourceCommentDto resourceCommentDto){
        ResourceComment resourceComment = new ResourceComment();
        resourceComment.setComment(resourceCommentDto.getComment());
        return resourceComment;
    }

    private ResourceCommentDto convertToResourceCommentDto(ResourceComment resourceComment){
        ResourceCommentDto resourceCommentDto = new ResourceCommentDto();
        resourceCommentDto.setComment(resourceComment.getComment());
        return resourceCommentDto;
    }

}
