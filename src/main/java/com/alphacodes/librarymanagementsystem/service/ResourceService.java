package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.ResourceDto;

import java.util.List;

public interface ResourceService {
    ResourceDto addResource(ResourceDto resourceDto);
    List<ResourceDto> getAllResources();
    ResourceDto getResourceById(Long resourceId);
    String deleteResource(Long resourceId);
    ResourceDto updateResource(Long resourceId, ResourceDto resourceDto);
    List<ResourceDto> searchResource(String keyword);
    List<ResourceDto> getResourcesByCategory(String category);
    List<ResourceDto> getResourcesByAuthor(String author);
    List<ResourceDto> getResourcesByTitle(String title);




}
