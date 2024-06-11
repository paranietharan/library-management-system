package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.ResourceDto;
import com.alphacodes.librarymanagementsystem.service.ResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    // Add a new resource
    @PostMapping("/resource")
    public ResponseEntity<ResourceDto> addResource(@RequestBody ResourceDto resourceDto) {
        return new ResponseEntity<>(resourceService.addResource(resourceDto), HttpStatus.CREATED);
    }

    // Get all resources
    @GetMapping("/resource")
    public List<ResourceDto> getAllResources() {
        return resourceService.getAllResources();
    }

    // Get a resource by its ID
    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<ResourceDto> getResourceById(@PathVariable Long resourceId) {
        ResourceDto resourceDto = resourceService.getResourceById(resourceId);
        return new ResponseEntity<>(resourceDto, HttpStatus.OK);
    }

    // Delete a resource by its ID
    @DeleteMapping("/resource/{resourceId}")
    public ResponseEntity<String> deleteResource(@PathVariable Long resourceId) {
        return new ResponseEntity<>(resourceService.deleteResource(resourceId),HttpStatus.NO_CONTENT);
    }

    // Update a resource by its ID
    @PutMapping("/resource/{resourceId}")
    public ResponseEntity<ResourceDto> updateResource(@PathVariable Long resourceId, @RequestBody ResourceDto resourceDto) {
        return new ResponseEntity<>(resourceService.updateResource(resourceId, resourceDto), HttpStatus.OK);
    }

    // Search for a resource by keyword
    @GetMapping("/resource/search")
    public List<ResourceDto> searchResource(@RequestParam String keyword) {
        return resourceService.searchResource(keyword);
    }

    // Get all resources by category
    @GetMapping("/resource/category/{category}")
    public List<ResourceDto> getResourcesByCategory(@PathVariable String category) {
        return resourceService.getResourcesByCategory(category);
    }

    // Get all resources by author
    @GetMapping("/resource/author/{author}")
    public List<ResourceDto> getResourcesByAuthor(@PathVariable String author) {
        return resourceService.getResourcesByAuthor(author);
    }

    // Get all resources by title
    @GetMapping("/resource/title/{title}")
    public List<ResourceDto> getResourcesByTitle(@PathVariable String title) {
        return resourceService.getResourcesByTitle(title);
    }

}
