package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.RatingDto;
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
    public RatingDto addResourceRating(Long resourceId, RatingDto ratingDto) {
        ResourceRating resourceRating = convertToResourceRating(ratingDto);
        resourceRating.setBook(resourceRepository.findById(resourceId).orElseThrow(
            () -> new RuntimeException("Resource not found with id " + resourceId)));

        ResourceRating newResourceRating = resourceRatingRepository.save(resourceRating);
        return convertToRatingDto(newResourceRating);
    }

    @Override
    public float getResourceRating(Long resourceId) {
        return calculateResourceRating(resourceId);
    }


    private float calculateResourceRating(Long resourceId) {
        List<ResourceRating> ratings = resourceRatingRepository.findByBook(resourceRepository.findById(resourceId).orElseThrow(
            () -> new RuntimeException("Resource not found with id " + resourceId)));

        if (ratings.isEmpty()) {
            throw new RuntimeException("No ratings found for resource with id " + resourceId);
        }

        float sum = 0;
        for (ResourceRating rating : ratings) {
            sum += rating.getRating();
        }

        return sum / ratings.size();
    }

    private ResourceRating convertToResourceRating(RatingDto ratingDto) {
        ResourceRating resourceRating = new ResourceRating();
        resourceRating.setMember(userRepository.findByUserID(ratingDto.getUserID()));
        resourceRating.setRating(ratingDto.getRating());
        return resourceRating;
    }

    private RatingDto convertToRatingDto(ResourceRating resourceRating) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setUserID(resourceRating.getMember().getUserID());
        ratingDto.setRating(resourceRating.getRating());
        return ratingDto;
    }

}
