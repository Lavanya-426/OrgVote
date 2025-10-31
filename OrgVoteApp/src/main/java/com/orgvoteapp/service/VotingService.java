package com.orgvoteapp.service;

import com.orgvoteapp.model.*;
import com.orgvoteapp.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VotingService {

    private final CandidateRepository candidateRepo;
    private final UserRepository voterRepo;

    private final Map<String, Integer> voteCount = new HashMap<>();

    public VotingService(CandidateRepository candidateRepo, UserRepository voterRepo) {
        this.candidateRepo = candidateRepo;
        this.voterRepo = voterRepo;
    }

    public String castVote(String voterId, String candidateId) {
        Optional<Voter> voterOpt = voterRepo.findAll().stream()
                .filter(v -> v.getVoterId().equals(voterId))
                .findFirst();

        if (voterOpt.isEmpty()) return "Voter not found";
        Voter voter = voterOpt.get();

        if (voter.isHasVoted()) return "Voter already voted";

        Optional<Candidate> candidateOpt = candidateRepo.findAll().stream()
                .filter(c -> c.getCandidateId().equals(candidateId))
                .findFirst();

        if (candidateOpt.isEmpty()) return "Candidate not found";

        voteCount.put(candidateId, voteCount.getOrDefault(candidateId, 0) + 1);
        voter.setHasVoted(true);
        voterRepo.save(voter);

        return "Vote cast successfully";
    }

    public Map<String, Integer> getResults() {
        return new HashMap<>(voteCount);
    }
}
