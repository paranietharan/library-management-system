package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.Model.Issue;
import com.alphacodes.librarymanagementsystem.Model.Resource;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.repository.IssueRepository;
import com.alphacodes.librarymanagementsystem.repository.ResourceRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Override
    public String issueResource(Long resourceId, int memberId, int librarianId) {
        Optional<Resource> resourceOpt = resourceRepository.findById(resourceId);
        Optional<User> memberOpt = userRepository.findById((int) memberId);
        Optional<User> librarianOpt = userRepository.findById((int) librarianId);

        if (resourceOpt.isPresent() && memberOpt.isPresent() && librarianOpt.isPresent()) {
            Resource resource = resourceOpt.get();
            User member = memberOpt.get();
            User librarian = librarianOpt.get();

            // Get resource availability count
            Integer resourceCount = resource.getAvailability();

            // Check resource availability
            if (resourceCount > 0) {
                // Decrease the availability count
                resource.setAvailability(resourceCount - 1);
                resourceRepository.save(resource);

                // Create new Issue record
                Issue issue = new Issue();
                issue.setBook(resource);
                issue.setMember(member);
                issue.setLibrarian(librarian);
                issue.setDate(new Date());

                issueRepository.save(issue);
                return "Resource issued successfully.";
            } else {
                return "Resource is not available.";
            }
        } else {
            return "Resource, Member, or Librarian not found.";
        }
    }

}
