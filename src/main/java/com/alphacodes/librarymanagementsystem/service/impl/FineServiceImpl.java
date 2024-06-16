package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.Model.Fine;
import com.alphacodes.librarymanagementsystem.repository.FineRepository;
import com.alphacodes.librarymanagementsystem.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FineServiceImpl implements FineService {
    @Autowired
    private FineRepository fineRepository;

    @Override
    public double calculateFine(int memberId) {
        // Calculate fine

        // search fine from fine table by memberId
        Fine fine = fineRepository.findByMember_Id(memberId);

        // if fine is not paid
        if (!fine.isPaidStatus()) {
            // get book issue date
            Date issueDate = fine.getResourceIssueDate();

            // Calculate the difference between current date and issue date
            long diff = new Date().getTime() - issueDate.getTime();

            // Calculate the number of days
            long diffDays = diff / (24 * 60 * 60 * 1000);

            // Calculate the fine
            double fineAmount = diffDays * 10;

            return fineAmount;
        } else {
            return 0;
        }
    }

    // Settle fine amount by member
    public String settleFine(int memberId) {
        // search fine from fine table by memberId
        Fine fine = fineRepository.findByMember_Id(memberId);

        // if fine is not paid
        if (!fine.isPaidStatus()) {
            // Set paid status to true
            fine.setPaidStatus(true);

            // Update fine table
            fineRepository.delete(fine);

            return "Fine settled successfully";
        } else {
            return "Fine already settled";
        }
    }

}
