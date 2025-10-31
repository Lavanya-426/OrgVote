package com.orgvoteapp.service;

import com.orgvoteapp.model.*;
import com.orgvoteapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepo;

    @Autowired
    private CandidateRepository candidateRepo;

    @Autowired
    private UserRepository voterRepo;

    public Election createElection(String orgName, LocalDate date, int duration,
                                   MultipartFile candidatesFile, MultipartFile votersFile) {
        try {
            Election election = new Election();
            election.setOrgName(orgName);
            election.setElectionDate(date);
            election.setDurationHours(duration);

            election = electionRepo.save(election);

            // Parse candidates CSV
            List<Candidate> candidates = parseCandidates(candidatesFile, election);
            candidateRepo.saveAll(candidates);

            // Parse voters CSV
            List<Voter> voters = parseVoters(votersFile, election);
            voterRepo.saveAll(voters);

            return election;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create election: " + e.getMessage());
        }
    }

    private List<Candidate> parseCandidates(MultipartFile file, Election election) throws Exception {
        List<Candidate> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        reader.readLine(); // skip header
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            Candidate c = new Candidate();
            c.setCandidateId(parts[0]);
            c.setName(parts[1]);
            c.setParty(parts[2]);
            c.setSymbol(parts[3]);
            c.setElection(election);
            list.add(c);
        }
        return list;
    }

    private List<Voter> parseVoters(MultipartFile file, Election election) throws Exception {
        List<Voter> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        reader.readLine(); // skip header
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            Voter v = new Voter();
            v.setVoterId(parts[0]);
            v.setName(parts[1]);
            v.setEmail(parts[2]);
            v.setElection(election);
            list.add(v);
        }
        return list;
    }
}
