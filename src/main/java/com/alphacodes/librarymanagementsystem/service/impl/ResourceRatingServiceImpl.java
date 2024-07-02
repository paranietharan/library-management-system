package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.RatingDto;
import com.alphacodes.librarymanagementsystem.Model.Resource;
import com.alphacodes.librarymanagementsystem.Model.ResourceRating;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.repository.ResourceRatingRepository;
import com.alphacodes.librarymanagementsystem.repository.ResourceRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.ResourceRatingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public float getResourceRating(Long resourceId) {
        return calculateResourceRating(resourceId);
    }

    @Override
    public RatingDto addOrUpdateResourceRating(Long resourceId, RatingDto ratingDto) {
        // Get resource using id
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(
                () -> new RuntimeException("Resource not found with id " + resourceId));

        // Get user using id
        User user = userRepository.findByUserID(ratingDto.getUserID());

        // Get existing rating
        Optional<ResourceRating> existingRating = resourceRatingRepository.findByResourceAndUser(resource, user);

        ResourceRating resourceRating;

        if (existingRating.isPresent()) {
            resourceRating = existingRating.get();
            resourceRating.setRating(ratingDto.getRating());
        } else {
            resourceRating = convertToResourceRating(ratingDto);
            resourceRating.setBook(resource);
            resourceRating.setMember(user);
        }

        ResourceRating savedRating = resourceRatingRepository.save(resourceRating);
        return convertToRatingDto(savedRating);
    }

    @Override
    public Float getResourceRatingByUserId(Long articleID, String userId) {
        Optional<ResourceRating> resourceRating = resourceRatingRepository.findByResourceAndUser(
                resourceRepository.findById(articleID).orElseThrow(
                        () -> new RuntimeException("Resource not found with id " + articleID)),
                userRepository.findByUserID(userId)
        );

        ResourceRating resourceRating1 = resourceRating.get();

        if (resourceRating1 == null) {
            throw new RuntimeException("No rating found for resource with id " + articleID + " and user with id " + userId);
        }

        return resourceRating1.getRating();
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
