package com.orgvoteapp.controller;

import com.orgvoteapp.model.Candidate;
import com.orgvoteapp.model.Election;
import com.orgvoteapp.model.Voter;
import com.orgvoteapp.repository.CandidateRepository;
import com.orgvoteapp.repository.ElectionRepository;
import com.orgvoteapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired private CandidateRepository candRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private ElectionRepository electionRepo;

    @GetMapping("/candidates")
    public Object listCandidates(){
        Election e = electionRepo.findTopByOrderByIdDesc();
        if(e==null || !e.isActive()) return new SimpleResp(false, "No active election");
        return candRepo.findAll();
    }

    @PostMapping("/vote")
    public Object vote(@RequestBody VoteDto dto){
        Optional<Voter> ou = userRepo.findByUsername(dto.getUsername());
        if(ou.isEmpty()) return new SimpleResp(false, "user not found");
        Voter user = ou.get();
        if(user.isVoted()) return new SimpleResp(false, "already voted");
        Election e = electionRepo.findTopByOrderByIdDesc();
        if(e==null || !e.isActive()) return new SimpleResp(false, "no active election");
        Optional<Candidate> oc = candRepo.findById(dto.getCandidateId());
        if(oc.isEmpty()) return new SimpleResp(false, "candidate not found");
        Candidate c = oc.get(); c.setVotes(c.getVotes()+1); candRepo.save(c);
        user.setVoted(true); userRepo.save(user);
        return new SimpleResp(true, "voted");
    }

    public static class VoteDto { private String username; private Long candidateId;
        public String getUsername(){return username;} public void setUsername(String u){this.username=u;}
        public Long getCandidateId(){return candidateId;} public void setCandidateId(Long id){this.candidateId=id;}
    }
    public static class SimpleResp { private boolean ok; private String msg; public SimpleResp(boolean o, String m){ok=o;msg=m;}
        public boolean isOk(){return ok;} public String getMsg(){return msg;}
    }
}
