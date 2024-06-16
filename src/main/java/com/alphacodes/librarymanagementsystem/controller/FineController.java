package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fine")
public class FineController {

    @Autowired
    private FineService fineService;

    @GetMapping("/calculate/{memberId}")
    public double calculateFine(@PathVariable int memberId) {
        return fineService.calculateFine(memberId);
    }

    @PostMapping("/settle/{memberId}")
    public String settleFine(@PathVariable int memberId) {
        return fineService.settleFine(memberId);
    }
}
