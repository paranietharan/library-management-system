package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.ResourceDto;
import com.alphacodes.librarymanagementsystem.service.ResourceService;
import com.alphacodes.librarymanagementsystem.util.ImageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    // Add a new resource
    @PostMapping("/addResource")
    public ResponseEntity<ResourceDto> addResource(
            @RequestParam String ISBN,
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam Integer availability,
            @RequestParam String category,
            @RequestParam String about,
            @RequestParam MultipartFile bookImg
    ) throws IOException {

        ResourceDto resourceDto = new ResourceDto();

        resourceDto.setResourceId(ISBN);
        resourceDto.setTitle(title);
        resourceDto.setAuthor(author);
        resourceDto.setAvailability(availability);
        resourceDto.setCategory(category);
        resourceDto.setAbout(about);

        if(bookImg != null) {
            resourceDto.setBookImg(ImageUtils.compressBytes(bookImg.getBytes()));
        }else {
            resourceDto.setBookImg(null);
        }

        return new ResponseEntity<>(resourceService.addResource(resourceDto), HttpStatus.CREATED);
    }

    // Get all resources
    @GetMapping("/all")
    public List<ResourceDto> getAllResources() {
        return resourceService.getAllResources();
    }

    // Get a resource by its ID
    @GetMapping("/get/{resourceId}")
    public ResponseEntity<ResourceDto> getResourceById(@PathVariable Long resourceId) {
        ResourceDto resourceDto = resourceService.getResourceById(resourceId);
        return new ResponseEntity<>(resourceDto, HttpStatus.OK);
    }

    // Delete a resource by its ID
    @DeleteMapping("/delete/{resourceId}")
    public ResponseEntity<String> deleteResource(@PathVariable Long resourceId) {
        return new ResponseEntity<>(resourceService.deleteResource(resourceId),HttpStatus.NO_CONTENT);
    }

    // Update a resource by its ID
    @PutMapping("/update/{resourceId}")
    public ResponseEntity<ResourceDto> updateResource(@PathVariable Long resourceId, @RequestBody ResourceDto resourceDto) {
        return new ResponseEntity<>(resourceService.updateResource(resourceId, resourceDto), HttpStatus.OK);
    }

    // Search for a resource by keyword
    @GetMapping("/search")
    public List<ResourceDto> searchResource(@RequestParam String keyword) {
        return resourceService.searchResource(keyword);
    }

    // Get all resources by category
    @GetMapping("/get/category/{category}")
    public List<ResourceDto> getResourcesByCategory(@PathVariable String category) {
        return resourceService.getResourcesByCategory(category);
    }

    // Get all resources by author
    @GetMapping("/get/author/{author}")
    public List<ResourceDto> getResourcesByAuthor(@PathVariable String author) {
        return resourceService.getResourcesByAuthor(author);
    }

    // Get all resources by title
    @GetMapping("/get/title/{title}")
    public List<ResourceDto> getResourcesByTitle(@PathVariable String title) {
        return resourceService.getResourcesByTitle(title);
    }

}
