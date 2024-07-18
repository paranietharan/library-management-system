package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.IssueDto;
import com.alphacodes.librarymanagementsystem.EmailService.EmailService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private EmailService emailService;

    // Function 2 issue resource
    @Override
    public String issueResource(Long resourceId, String memberId) {
        Optional<Resource> resourceOpt = resourceRepository.findById(resourceId);
        Optional<User> memberOpt = Optional.ofNullable(userRepository.findByUserID(memberId));

        if (resourceOpt.isEmpty()) {
            return "Resource not found.";
        }

        if (memberOpt.isEmpty()) {
            return "Member not found.";
        }

        User member = memberOpt.get();

        // Check if the member needs to pay a fine
        if (fineService.calculateFine(memberId) > 0) {
            return "Please pay the fine first.";
        }

        // Check if the member already has an issued book that hasn't been returned
        Optional<Issue> nonReturnedIssues = issueRepository.findNonReturnIssueByUserId(memberId);
        if (nonReturnedIssues.isPresent()) {
            return "You already have a book and haven't returned it yet.";
        }

        Resource resource = resourceOpt.get();

        // Get resource availability count
        Integer resourceCount = resource.getNo_of_copies();

        // Check resource availability
        if (resourceCount > 0) {
            // Decrease the availability count
            resource.setNo_of_copies(resourceCount - 1);
            resourceRepository.save(resource);

            // Create new Issue record
            Issue issue = new Issue();
            issue.setBook(resource);
            issue.setMember(member);
            issue.setDate(new Date());
            issue.setReturned(false);
            issue.setFinePaid(false);

            issueRepository.save(issue);

            // Create a fine record
            Fine fine = new Fine();
            fine.setPaidStatus(false);
            fine.setAmount(0);
            fine.setMember(member);
            fine.setResourceIssueDate(new Date());
            fine.setIssue(issue);

            // Save the fine record
            fineRepository.save(fine);

            // Send email to the member
            emailService.sendSimpleEmail(
                    member.getEmailAddress(),
                    "Resource Issued",
                    "Hi " + member.getFirstName() + ",\n\n" +
                            "The resource " + resource.getTitle() + " has been issued to you.\n" +
                            "Please return the resource within 15 days to avoid any fines.\n\n" +
                            "Thank you,\n" +
                            "Library Management System"
            );

            return "Resource issued successfully.";
        } else {
            return "Resource is not available.";
        }
    }

    // get Return resources
    @Override
    public String returnResource(String memberId) {
        Optional<User> memberOpt = Optional.ofNullable(userRepository.findByUserID(memberId));

        if (memberOpt.isPresent()) {
            User member = memberOpt.get();

            // Find the issue record
            Optional<Issue> issueOpt = issueRepository.findNonReturnIssueByUserId(memberId);

            if (issueOpt.isPresent()) {
                Issue issue = issueOpt.get();

                // check user has returned the book or not
                if(issue.isReturned()){
                    return "Resource already returned.";
                }

                // Get the resource
                Resource resource = issue.getBook();
                // Increase the availability count of the resource
                resource.setNo_of_copies(resource.getNo_of_copies() + 1);
                resourceRepository.save(resource);

                // Calculate fine
                double fineAmount = fineService.calculateFine(memberId);
                if(fineAmount == 0){
                    // if the fine is zero then delete the fine record
                    Fine fine = fineRepository.findByUserIdAndIssueId(memberId, issue.getIssueId());
                    if (fine != null) {
                        fineRepository.delete(fine);

                        // set the issue record as fine paid
                        issue.setFinePaid(true);
                    }
                }

                // Set the issue record as returned
                issue.setReturned(true);

                issueRepository.save(issue);

                // Send email to the member about the book return
                emailService.sendSimpleEmail(
                        member.getEmailAddress(),
                        "Resource Returned",
                        "Hi " + member.getFirstName() + ",\n\n" +
                                "The resource " + resource.getTitle() + " has been returned successfully.\n" +
                                "Thank you for returning the resource.\n\n" +
                                "Thank you,\n" +
                                "Library Management System"
                );

                return "Resource returned successfully.";
            } else {
                return "No issue record found for this resource and member.";
            }
        } else {
            return "Resource or Member not found.";
        }
    }

    @Override
    public IssueDto checkResource(String memberId) {
        Optional<User> memberOpt = Optional.ofNullable(userRepository.findByUserID(memberId));

        if (memberOpt.isPresent()) {
            User member = memberOpt.get();

            // Find the issue record
            Optional<Issue> issueOpt = issueRepository.findNonReturnIssueByUserId(memberId);

            if (issueOpt.isPresent()) {
                Issue issue = issueOpt.get();

                // Get the resource
                Resource resource = issue.getBook();

                IssueDto issueDto = new IssueDto();
                issueDto.setIssueId(issue.getIssueId());
                issueDto.setDate(issue.getDate());
                issueDto.setReturned(issue.isReturned());
                issueDto.setFinePaid(issue.isFinePaid());
                issueDto.setResourceId(resource.getId());

                return issueDto;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public List<IssueDto> getHistory(String memberId) {
        Optional<User> memberOpt = Optional.ofNullable(userRepository.findByUserID(memberId));

        if (memberOpt.isPresent()) {
            User member = memberOpt.get();

            // Find the issue record
            List<Issue> issueList = issueRepository.findReturnIssueByUserId(memberId);

            if (issueList != null) {
                return issueList.stream().map(issue -> {
                    IssueDto issueDto = new IssueDto();
                    issueDto.setIssueId(issue.getIssueId());
                    issueDto.setDate(issue.getDate());
                    issueDto.setReturned(issue.isReturned());
                    issueDto.setFinePaid(issue.isFinePaid());
                    issueDto.setResourceId(issue.getBook().getId());

                    return issueDto;
                }).collect(Collectors.toList());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // Function 3: get all the issued resources count
    @Override
    public int getAllIssuedResourcesCount() {
        // Find all issues where the resource has not been returned
        List<Issue> issuedResources = issueRepository.findAllByReturned(false);

        // Return the count of such issues
        return issuedResources.size();
    }

}
