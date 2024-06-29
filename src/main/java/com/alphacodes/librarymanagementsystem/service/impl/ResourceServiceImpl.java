package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.ResourceDto;
import com.alphacodes.librarymanagementsystem.Model.Resource;
import com.alphacodes.librarymanagementsystem.repository.ResourceRepository;
import com.alphacodes.librarymanagementsystem.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public ResourceDto addResource(ResourceDto resourceDto) {
        Resource resource = convertToResource(resourceDto);
        Resource newResource = resourceRepository.save(resource);
        return convertToResourceDto(newResource);
    }

    @Override
    public List<ResourceDto> getAllResources() {
        List<Resource> resources = resourceRepository.findAll();
        return resources.stream().map(this::convertToResourceDto).collect(Collectors.toList());
    }

    @Override
    public ResourceDto getResourceById(String resourceId) {
        Resource resource = resourceRepository.findByResourceId(resourceId);
        return convertToResourceDto(resource);
    }

    @Override
    public String deleteResource(String resourceId) {
        Resource resource = resourceRepository.findByResourceId(resourceId);
        resourceRepository.delete(resource);
        return "Resource deleted Successfully";
    }

    @Override
    public ResourceDto updateResource(String resourceId, ResourceDto resourceDto) {
        Resource resource = resourceRepository.findByResourceId(resourceId);

        // Check if the resource exists
        if(resource == null) {
            return null;
        }

        resource.setResourceId(resourceDto.getResourceId());
        resource.setAuthor(resourceDto.getAuthor());
        resource.setCategory(resourceDto.getCategory());
        resource.setTitle(resourceDto.getTitle());
        resource.setAvailability(resourceDto.getAvailability());
        resource.setAbout(resourceDto.getAbout());
        resource.setBookImg(resourceDto.getBookImg());

        Resource updatedResource = resourceRepository.save(resource);

        return convertToResourceDto(updatedResource);
    }

    public List<ResourceDto> searchResource(String keyword) {
        List<Resource> resources = resourceRepository.findByTitleMatchKeyword(keyword);
        return resources.stream().map(this::convertToResourceDto).collect(Collectors.toList());
    }

    public List<ResourceDto> getResourcesByCategory(String category) {
        List<Resource> resources = resourceRepository.findByCategory(category);
        return resources.stream().map(this::convertToResourceDto).collect(Collectors.toList());
    }

    public List<ResourceDto> getResourcesByAuthor(String author) {
        List<Resource> resources = resourceRepository.findByKeywordRelatedAuthors(author);

        return resources.stream().map(this::convertToResourceDto).collect(Collectors.toList());
    }

    public List<ResourceDto> getResourcesByTitle(String title) {
        List<Resource> resources = resourceRepository.findByTitle(title);
        return resources.stream().map(this::convertToResourceDto).collect(Collectors.toList());
    }


    private ResourceDto convertToResourceDto(Resource resource){
        ResourceDto resourceDto = new ResourceDto();

        resourceDto.setAuthor(resource.getAuthor());
        resourceDto.setCategory(resource.getCategory());
        resourceDto.setTitle(resource.getTitle());
        resourceDto.setAvailability(resource.getAvailability());
        resourceDto.setAbout(resource.getAbout());
        resourceDto.setBookImg(resource.getBookImg());
        resourceDto.setResourceId(resource.getResourceId());

        return resourceDto;
    }

    private Resource convertToResource(ResourceDto resourceDto){
        Resource resource = new Resource();

        resource.setResourceId(resourceDto.getResourceId());
        resource.setAuthor(resourceDto.getAuthor());
        resource.setCategory(resourceDto.getCategory());
        resource.setTitle(resourceDto.getTitle());
        resource.setAvailability(resourceDto.getAvailability());
        resource.setAbout(resourceDto.getAbout());
        resource.setBookImg(resourceDto.getBookImg());

        return resource;
    }

    // Code for get resource count - aka avilability
    public Integer getAvailability(Long resourceId) {
        Optional<Resource> resourceOpt = resourceRepository.findById(resourceId);
        return resourceOpt.map(Resource::getAvailability).orElse(null);
    }
}
