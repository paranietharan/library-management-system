package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.ResourceDto;
import com.alphacodes.librarymanagementsystem.DTO.ResourceViewDto;
import com.alphacodes.librarymanagementsystem.Model.Resource;
import com.alphacodes.librarymanagementsystem.repository.*;
import com.alphacodes.librarymanagementsystem.service.ResourceService;
import com.alphacodes.librarymanagementsystem.util.ImageUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final IssueRepository issueRepository;
    private final ResourceCommentRepository resourceCommentRepository;
    private final ResourceRatingRepository resourceRatingRepository;
    private final FineRepository fineRepository;
    private final ReservationRepository reservationRepository;

    public ResourceServiceImpl(
            ResourceRepository resourceRepository,
            IssueRepository issueRepository,
            ResourceCommentRepository resourceCommentRepository,
            ResourceRatingRepository resourceRatingRepository,
            FineRepository fineRepository,
            ReservationRepository reservationRepository
    ) {
        this.resourceRepository = resourceRepository;

        this.issueRepository = issueRepository;
        this.reservationRepository = reservationRepository;
        this.fineRepository = fineRepository;
        this.resourceCommentRepository = resourceCommentRepository;
        this.resourceRatingRepository = resourceRatingRepository;
    }

    @Override
    public ResourceDto addResource(ResourceDto resourceDto) {
        Resource resource = convertToResource(resourceDto);
        Resource newResource = resourceRepository.save(resource);
        return convertToResourceDto(newResource);
    }

    @Override
    public List<ResourceViewDto> getAllResources() {
        List<Resource> resources = resourceRepository.findAll();
        return resources
                .stream()
                .map(this::convertToResourceViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResourceViewDto getResourceByISBN(String isbn) {
        Resource resource = resourceRepository.findByIsbn(isbn);
        return convertToResourceViewDto(resource);
    }

    @Override
    public String deleteResource(Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId).orElse(null);

        // check if the resource exists
        if(resource == null) {
            return "Resource not found";
        }


        // 01. Reservation Table
        reservationRepository
                .findByResource(resource)
                        .forEach(reservationRepository::delete);

        // 02. fine Table
        issueRepository
                .findByResource(resource)
                        .forEach(issue -> fineRepository
                                .findByIssue(issue)
                                .forEach(fineRepository::delete));


        // 02. Issue Table
        issueRepository
                .findByResource(resource)
                        .forEach(issueRepository::delete);

        // 04. Resource Comment Table
        resourceCommentRepository
                .findByResource(resource)
                        .forEach(resourceCommentRepository::delete);

        // 05. Resource Rating Table
        resourceRatingRepository
                .findByResource(resource)
                        .forEach(resourceRatingRepository::delete);

        resourceRepository.delete(resource);
        return "Resource deleted Successfully";
    }

    @Override
    public ResourceDto updateResource(Long resourceId, ResourceDto resourceDto) {
        Optional<Resource> OptResource = resourceRepository.findById(resourceId);
        Resource resource = OptResource.orElse(null);

        // Check if the resource exists
        if(resource == null) {
            return null;
        }

        resource.setIsbn(resourceDto.getIsbn());
        resource.setAuthor(resourceDto.getAuthor());
        resource.setCategory(resourceDto.getCategory());
        resource.setTitle(resourceDto.getTitle());
        resource.setNo_of_copies(resourceDto.getNo_of_copies());
        resource.setAbout(resourceDto.getAbout());

        if(resourceDto.getBookImg() != null) {
            //resource.setBookImg(resourceDto.getBookImg());
            byte[] decompressedImage = ImageUtils.decompressBytes(resourceDto.getBookImg());
            resource.setBookImg(decompressedImage);
        } else {
            resource.setBookImg(null);
        }

        Resource updatedResource = resourceRepository.save(resource);

        return convertToResourceDto(updatedResource);
    }

    public List<ResourceViewDto> searchResource(String keyword) {
        List<Resource> resources = resourceRepository.findByTitleMatchKeyword(keyword);
        return resources
                .stream()
                .map(this::convertToResourceViewDto)
                .collect(Collectors.toList());
    }

    public List<ResourceViewDto> getResourcesByCategory(String category) {
        List<Resource> resources = resourceRepository.findByCategory(category);
        return resources
                .stream()
                .map(this::convertToResourceViewDto)
                .collect(Collectors.toList());
    }

    public List<ResourceViewDto> getResourcesByAuthor(String author) {
        List<Resource> resources = resourceRepository.findByKeywordRelatedAuthors(author);

        return resources
                .stream()
                .map(this::convertToResourceViewDto)
                .collect(Collectors.toList());
    }

    public List<ResourceViewDto> getResourcesByTitle(String title) {
        List<Resource> resources = resourceRepository.findByTitle(title);
        return resources
                .stream()
                .map(this::convertToResourceViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResourceViewDto getResourceById(Long resourceId) {
        Optional<Resource> resourceOpt = resourceRepository.findById(resourceId);
        return resourceOpt.map(this::convertToResourceViewDto).orElse(null);
    }

    @Override
    public Integer getAvailability(Long resourceId) {
        Optional<Resource> resourceOpt = resourceRepository.findById(resourceId);
        return resourceOpt.map(Resource::getNo_of_copies).orElse(null);
    }

    @Override
    public Integer getTotalResources() {
        int totalResources = 0;
        List<Resource> resources = resourceRepository.findAll();

        if(resources.isEmpty()) {
            return 0;
        } else {
            for(Resource resource: resources) {
                totalResources += resource.getNo_of_copies();
            }
            return totalResources;
        }
    }

    // Code for converting ResourceDto to Resource
    private Resource convertToResource(ResourceDto resourceDto){
        Resource resource = new Resource();

        resource.setIsbn(resourceDto.getIsbn());
        resource.setAuthor(resourceDto.getAuthor());
        resource.setCategory(resourceDto.getCategory());
        resource.setTitle(resourceDto.getTitle());
        resource.setNo_of_copies(resourceDto.getNo_of_copies());
        resource.setAbout(resourceDto.getAbout());

        if(resourceDto.getBookImg() != null) {
            //resource.setBookImg(resourceDto.getBookImg());
            byte[] decompressedImage = ImageUtils.decompressBytes(resourceDto.getBookImg());
            resource.setBookImg(decompressedImage);
        } else {
            resource.setBookImg(null);
        }

        return resource;
    }

    // Code for converting Resource to ResourceDto
    private ResourceDto convertToResourceDto(Resource resource){
        ResourceDto resourceDto = new ResourceDto();

        resourceDto.setAuthor(resource.getAuthor());
        resourceDto.setCategory(resource.getCategory());
        resourceDto.setTitle(resource.getTitle());
        resourceDto.setNo_of_copies(resource.getNo_of_copies());
        resourceDto.setAbout(resource.getAbout());
        resourceDto.setBookImg(resource.getBookImg());
        resourceDto.setIsbn(resource.getIsbn());

        return resourceDto;
    }

    // Code for converting Resource to ResourceViewDto
    private ResourceViewDto convertToResourceViewDto(Resource resource) {
        ResourceViewDto resourceViewDto = new ResourceViewDto();

        resourceViewDto.setResourceId(resource.getId());
        resourceViewDto.setIsbn(resource.getIsbn());
        resourceViewDto.setTitle(resource.getTitle());
        resourceViewDto.setAuthor(resource.getAuthor());
        resourceViewDto.setNo_of_copies(resource.getNo_of_copies());
        resourceViewDto.setCategory(resource.getCategory());
        resourceViewDto.setAbout(resource.getAbout());
        resourceViewDto.setBookImg(resource.getBookImg());

        return resourceViewDto;
    }
}
