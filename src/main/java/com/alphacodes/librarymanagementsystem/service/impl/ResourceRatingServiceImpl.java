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
    public RatingDto addResourceRating(Long rID, RatingDto ratingDto) {
        ResourceRating resourceRating = convertToResourceRating(ratingDto);
        resourceRating.setBook(resourceRepository.findById(rID).orElseThrow(
            () -> new RuntimeException("Resource not found with id " + rID)));

        ResourceRating newResourceRating = resourceRatingRepository.save(resourceRating);
        return convertToRatingDto(newResourceRating);
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

    private ResourceRating convertToResourceRating(RatingDto ratingDto) {
        ResourceRating resourceRating = new ResourceRating();
        resourceRating.setMember(userRepository.findById(ratingDto.getUserID()).orElseThrow(
            () -> new RuntimeException("User not found with id " + ratingDto.getUserID())));
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
