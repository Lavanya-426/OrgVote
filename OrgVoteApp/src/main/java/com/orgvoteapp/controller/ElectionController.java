package com.orgvoteapp.controller;

import com.orgvoteapp.model.Election;
import com.orgvoteapp.service.ElectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/elections")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @PostMapping("/create")
    public String createElection(@RequestParam String orgName,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate electionDate,
                                 @RequestParam int duration,
                                 @RequestParam("candidates") MultipartFile candidatesFile,
                                 @RequestParam("voters") MultipartFile votersFile) {
        Election e = electionService.createElection(orgName, electionDate, duration, candidatesFile, votersFile);
        return "Election created successfully for organization: " + e.getOrgName();
    }
}
