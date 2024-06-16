package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.Model.Fine;
import com.alphacodes.librarymanagementsystem.Model.Issue;
import com.alphacodes.librarymanagementsystem.Model.Resource;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.repository.FineRepository;
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

    @Autowired
    private FineRepository fineRepository;

    @Autowired
    private FineServiceImpl fineService;


    // Function 2 issue resource
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

                // Create a fine record
                Fine fine = new Fine();
                fine.setPaidStatus(false);
                fine.setAmount(0);
                fine.setMember(member);
                fine.setLibrarian(librarian);
                fine.setResourceIssueDate(new Date());
                // Save the fine record
                fineRepository.save(fine);

                return "Resource issued successfully.";
            } else {
                return "Resource is not available.";
            }
        } else {
            return "Resource, Member, or Librarian not found.";
        }
    }


    // get Return resources
    //@Override
    public String returnResource(Long resourceId, int memberId) {
        Optional<Resource> resourceOpt = resourceRepository.findById(resourceId);
        Optional<User> memberOpt = userRepository.findById(memberId);

        if (resourceOpt.isPresent() && memberOpt.isPresent()) {
            Resource resource = resourceOpt.get();
            User member = memberOpt.get();

            // Find the issue record
            Optional<Issue> issueOpt = issueRepository.findIssueByMemberId(memberId);

            if (issueOpt.isPresent()) {
                Issue issue = issueOpt.get();

                // Delete the issue record
                issueRepository.delete(issue);

                // Increase the availability count of the resource
                resource.setAvailability(resource.getAvailability() + 1);
                resourceRepository.save(resource);


                // Calculate fine
                double fineAmount = fineService.calculateFine(memberId);
                if(fineAmount == 0){
                    // if the fine is zero then delete the fine record
                    Fine fine = fineRepository.findByMember_Id(memberId);
                    fineRepository.delete(fine);
                }

                return "Resource returned successfully.";
            } else {
                return "No issue record found for this resource and member.";
            }
        } else {
            return "Resource or Member not found.";
        }
    }

}
