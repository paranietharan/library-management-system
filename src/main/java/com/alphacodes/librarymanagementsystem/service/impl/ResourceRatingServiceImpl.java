package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.ResourceRatingDto;
import com.alphacodes.librarymanagementsystem.Model.ResourceRating;
import com.alphacodes.librarymanagementsystem.repository.ResourceRatingRepository;
import com.alphacodes.librarymanagementsystem.repository.ResourceRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.ResourceRatingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceRatingServiceImpl implements ResourceRatingService {

    private final ResourceRatingRepository resourceRatingRepository;
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    public ResourceRatingServiceImpl(ResourceRatingRepository resourceRatingRepository, ResourceRepository resourceRepository, UserRepository userRepository) {
        this.resourceRatingRepository = resourceRatingRepository;
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResourceRatingDto addResourceRating(int userID, Long rID, ResourceRatingDto rating) {
        ResourceRating resourceRating = convertToResourceRating(rating);
        resourceRating.setBook(resourceRepository.findById(rID).orElseThrow(
            () -> new RuntimeException("Resource not found with id " + rID)));
        resourceRating.setMember(userRepository.findByUserID(userID).orElse(null));

        ResourceRating newResourceRating = resourceRatingRepository.save(resourceRating);
        return convertToResourceRatingDto(newResourceRating);
    }

    @Override
    public float getResourceRating(Long rID) {
        return calculateResourceRating(rID);
    }


    private float calculateResourceRating(Long rID) {
        List<ResourceRating> ratings = resourceRatingRepository.findByBook(resourceRepository.findById(rID).orElseThrow(
            () -> new RuntimeException("Resource not found with id " + rID)));

        if (ratings.isEmpty()) {
            throw new RuntimeException("No ratings found for resource with id " + rID);
        }

        float sum = 0;
        for (ResourceRating rating : ratings) {
            sum += rating.getRating();
        }

        return sum / ratings.size();
    }

    private ResourceRating convertToResourceRating(ResourceRatingDto resourceRatingDto) {
        ResourceRating resourceRating = new ResourceRating();
        resourceRating.setRating(resourceRatingDto.getRating());
        return resourceRating;
    }

    private ResourceRatingDto convertToResourceRatingDto(ResourceRating resourceRating) {
        ResourceRatingDto resourceRatingDto = new ResourceRatingDto();
        resourceRatingDto.setRating(resourceRating.getRating());
        return resourceRatingDto;
    }

}
