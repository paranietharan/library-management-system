package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.FineDto;
import com.alphacodes.librarymanagementsystem.Model.Fine;
import com.alphacodes.librarymanagementsystem.repository.FineRepository;
import com.alphacodes.librarymanagementsystem.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FineServiceImpl implements FineService {
    // Fine amount constant
    private static final double FINE_AMOUNT = 0.5;
    @Autowired
    private FineRepository fineRepository;

    @Override
    public double calculateFine(String memberId) {
        // Calculate fine

        // search fine from fine table by memberId
        Fine fine = fineRepository.findByUserID(memberId);

        // If they don't have any fine
        if (fine == null) {
            return 0;
        }

        // if fine is not paid
        // when pay fine record will be deleted from the db
        if (!fine.isPaidStatus()) {
            // get book issue date
            Date issueDate = fine.getResourceIssueDate();

            // Calculate the difference between current date and issue date
            long diff = new Date().getTime() - issueDate.getTime();

            // Calculate the number of days
            long diffDays = diff / (24 * 60 * 60 * 1000);

            // Calculate the fine
            double fineAmount = diffDays * FINE_AMOUNT;

            return fineAmount;
        } else {
            return 0;
        }
    }

    // Settle fine amount by member
    @Override
    public String settleFine(String memberId) {
        // search fine from fine table by memberId
        Fine fine = fineRepository.findByUserID(memberId);

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

    // Get all unpaid fine details
    @Override
    public List<FineDto> getAllUnpaidFine() {
        // get all unpaid fines from db
        List<Fine> fineList = fineRepository.findByPaidStatus(false);

        // create a list of fineDto
        List<FineDto> fineDtoList = new ArrayList<>();
        // convert all fines to fineDto
        for (Fine fine : fineList) {
            FineDto fineDto = new FineDto();

            fineDto.setFineId(fine.getFineId());
            fineDto.setAmount(fine.getAmount());
            fineDto.setPaidStatus(fine.isPaidStatus());
            fineDto.setResourceIssueDate(fine.getResourceIssueDate());
            fineDto.setMemberId(fine.getMember().getUserID());
            fineDto.setResourceId(fine.getIssue().getBook().getId());

            fineDtoList.add(fineDto);
        }

        return fineDtoList;
    }

    // Get all fine history of user
    @Override
    public List<FineDto> getFineHistoryByUser(String memberId) {
        // get all fines from db by memberId
        List<Fine> fineList = fineRepository.findAllFinesByMemberID(memberId);

        // create a list of fineDto
        List<FineDto> fineDtoList = new ArrayList<>();
        // convert all fines to fineDto
        for (Fine fine : fineList) {
            FineDto fineDto = new FineDto();

            fineDto.setFineId(fine.getFineId());
            fineDto.setAmount(fine.getAmount());
            fineDto.setPaidStatus(fine.isPaidStatus());
            fineDto.setResourceIssueDate(fine.getResourceIssueDate());
            fineDto.setMemberId(fine.getMember().getUserID());
            fineDto.setResourceId(fine.getIssue().getBook().getId());

            fineDtoList.add(fineDto);
        }

        return fineDtoList;
    }


    // Automation -----------------
    public void calculateFine(Fine fine) {
        Date currentDate = new Date();
        Date issueDate = fine.getResourceIssueDate();

        // Calculate the difference in days
        long diffInMillies = Math.abs(currentDate.getTime() - issueDate.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        // Fine calculation: After 14 days, 10 per day, maximum 600
        if (diffInDays > 14) {
            long overdueDays = diffInDays - 14;
            double fineAmount = Math.min(overdueDays * 10, 600);

            fine.setAmount(fineAmount);
            fine.setPaidStatus(false);

            fineRepository.save(fine);
        }
    }

    public void checkAndUpdateFines() {
        // Fetch all fines and calculate fines where necessary
        List<Fine> fines = fineRepository.findAll();
        for (Fine fine : fines) {
            calculateFine(fine);
        }
    }
}
