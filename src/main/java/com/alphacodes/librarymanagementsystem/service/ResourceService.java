package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.ResourceDto;
import com.alphacodes.librarymanagementsystem.DTO.ResourceViewDto;

import java.util.List;

public interface ResourceService {
    ResourceDto addResource(ResourceDto resourceDto);
    List<ResourceViewDto> getAllResources();
    ResourceViewDto getResourceByISBN(String isbn);
    String deleteResource(Long resourceId);
    ResourceDto updateResource(Long resourceId, ResourceDto resourceDto);

    // Search for Resource
    List<ResourceViewDto> searchResource(String keyword);
    List<ResourceViewDto> getResourcesByCategory(String category);
    List<ResourceViewDto> getResourcesByAuthor(String author);
    List<ResourceViewDto> getResourcesByTitle(String title);

    ResourceViewDto getResourceById(Long resourceId);
    Integer getAvailability(Long resourceId);

    Integer getTotalResources();
}
