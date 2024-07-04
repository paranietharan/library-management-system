package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.IssueDto;
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


    // Function 2 issue resource
    @Override
    public String issueResource(Long resourceId, String memberId) {
        Optional<Resource> resourceOpt = resourceRepository.findById(resourceId);
        Optional<User> memberOpt = Optional.ofNullable(userRepository.findByUserID(memberId));

        // before issue resource check if hey need to pay fine or not
        // if fine is not paid then they can't issue the resource
        if(fineService.calculateFine(memberId) > 0){
            return "Please pay the fine first.";
        }

        // also check if the user already lend a book and not return it
        if(issueRepository.findNonReturnIssueByUserIdAndResourceId(memberId, resourceId).isPresent()){
            return "You already have this book.";
        }

        if (resourceOpt.isPresent() && memberOpt.isPresent() ) {
            Resource resource = resourceOpt.get();
            User member = memberOpt.get();

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

                return "Resource issued successfully.";
            } else {
                return "Resource is not available.";
            }
        } else {
            return "Resource, Member, or Librarian not found.";
        }
    }


    // get Return resources
    @Override
    public String returnResource(Long resourceId, String memberId) {
        Optional<Resource> resourceOpt = resourceRepository.findById(resourceId);
        Optional<User> memberOpt = Optional.ofNullable(userRepository.findByUserID(memberId));

        if (resourceOpt.isPresent() && memberOpt.isPresent()) {
            Resource resource = resourceOpt.get();
            User member = memberOpt.get();

            // Find the issue record
            Optional<Issue> issueOpt = issueRepository.findNonReturnIssueByUserIdAndResourceId(memberId, resourceId);

            if (issueOpt.isPresent()) {
                Issue issue = issueOpt.get();

                // Increase the availability count of the resource
                resource.setNo_of_copies(resource.getNo_of_copies() + 1);
                resourceRepository.save(resource);


                // Calculate fine
                double fineAmount = fineService.calculateFine(memberId);

                if(fineAmount == 0){
                    // Delete the issue record
                    //issueRepository.delete(issue);

                    // if the fine is zero then delete the fine record
                    Fine fine = fineRepository.findByUserIdAndIssueId(memberId, issue.getIssueId());
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

    // get issue history of a member
    @Override
    public List<IssueDto> getIssueHistory(String memberId) {
        List<Issue> issueList = issueRepository.findIssueByUserId(memberId);
        return issueList
                .stream()
                .map(this::convertToIssueDto)
                .collect(Collectors.toList());
    }

    private IssueDto convertToIssueDto(Issue issue) {
        IssueDto issueDto = new IssueDto();

        issueDto.setIssueId(issue.getIssueId());
        issueDto.setDate(issue.getDate());
        issueDto.setReturned(issue.isReturned());
        issueDto.setFinePaid(issue.isFinePaid());

        return issueDto;
    }
}
