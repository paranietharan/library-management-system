package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.ResourceDto;
import com.alphacodes.librarymanagementsystem.DTO.ResourceViewDto;
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
            @RequestParam(required = false) String ISBN,
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam Integer no_of_copies,
            @RequestParam String category,
            @RequestParam String about,
            @RequestParam(required = false) MultipartFile bookImg
    ) throws IOException {

        ResourceDto resourceDto = new ResourceDto();

        resourceDto.setIsbn(ISBN);
        resourceDto.setTitle(title);
        resourceDto.setAuthor(author);
        resourceDto.setNo_of_copies(no_of_copies);
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
    public List<ResourceViewDto> getAllResources() {
        return resourceService.getAllResources();
    }

    // Get a resource by its ISBN
    @GetMapping("/get/{isbn}")
    public ResponseEntity<ResourceViewDto> getResourceByISBN(@PathVariable String isbn) {
        ResourceViewDto resourceViewDto = resourceService.getResourceByISBN(isbn);
        return new ResponseEntity<>(resourceViewDto, HttpStatus.OK);
    }

    // Get a resource by its ID
    @GetMapping("/get/id/{resourceId}")
    public ResponseEntity<ResourceViewDto> getResourceById(@PathVariable Long resourceId) {
        ResourceViewDto resourceViewDto = resourceService.getResourceById(resourceId);
        return new ResponseEntity<>(resourceViewDto, HttpStatus.OK);
    }

    // Delete a resource by its ID
    @DeleteMapping("/delete/{resourceId}")
    public ResponseEntity<String> deleteResource(@PathVariable Long resourceId) {
        return new ResponseEntity<>(resourceService.deleteResource(resourceId),HttpStatus.NO_CONTENT);
    }

    // Update a resource by its ID
    @PutMapping("/update/{resourceId}")
    public ResponseEntity<ResourceDto> updateResource(
            // Get resource id from the path
            @PathVariable Long resourceId,

            // Get the resource details from the request body
            @RequestParam(required = false) String ISBN,// useful when updating the resource ID
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam Integer no_of_copies,
            @RequestParam String category,
            @RequestParam String about,
            @RequestParam(required = false) MultipartFile bookImg
    ) {
        ResourceDto resourceDto = new ResourceDto();

        // Set the resource details
        resourceDto.setTitle(title);
        resourceDto.setAuthor(author);
        resourceDto.setNo_of_copies(no_of_copies);
        resourceDto.setCategory(category);
        resourceDto.setAbout(about);
        resourceDto.setIsbn(ISBN);

        // Set the resource image
        if(bookImg != null) {
            try {
                resourceDto.setBookImg(ImageUtils.compressBytes(bookImg.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            resourceDto.setBookImg(null);
        }

        return new ResponseEntity<>(resourceService.updateResource(resourceId, resourceDto), HttpStatus.OK);
    }

    // Search for a resource by keyword
    @GetMapping("/search")
    public ResponseEntity<List<ResourceViewDto>> searchResource(@RequestParam String keyword) {
        //return resourceService.searchResource(keyword);
        List<ResourceViewDto> resourceDto = resourceService.searchResource(keyword);

        if(resourceDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(resourceDto, HttpStatus.OK);
        }
    }

    // Get all resources by category
    @GetMapping("/get/category/{category}")
    public ResponseEntity<List<ResourceViewDto>> getResourcesByCategory(@PathVariable String category) {
        List<ResourceViewDto> resourceDto = resourceService.getResourcesByCategory(category);

        if(resourceDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(resourceDto, HttpStatus.OK);
        }
    }

    // Get all resources by author
    @GetMapping("/get/author/{author}")
    public ResponseEntity<List<ResourceViewDto>> getResourcesByAuthor(@PathVariable String author) {
        //return resourceService.getResourcesByAuthor(author);
        List<ResourceViewDto> resourceDto = resourceService.getResourcesByAuthor(author);

        if(resourceDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(resourceDto, HttpStatus.OK);
        }
    }

    // Get all resources by title
    @GetMapping("/get/title/{title}")
    public ResponseEntity<List<ResourceViewDto>> getResourcesByTitle(@PathVariable String title) {
        //return resourceService.getResourcesByTitle(title);
        List<ResourceViewDto> resourceDto = resourceService.getResourcesByTitle(title);

        if(resourceDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(resourceDto, HttpStatus.OK);
        }
    }

    // Get Book Availability
    @GetMapping("/availability/{resourceId}")
    public ResponseEntity<Integer> getAvailability(@PathVariable Long resourceId) {
        return new ResponseEntity<>(resourceService.getAvailability(resourceId), HttpStatus.OK);
    }

    // get total number of resources
    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalResources() {
        return new ResponseEntity<>(resourceService.getTotalResources(), HttpStatus.OK);
    }
}
