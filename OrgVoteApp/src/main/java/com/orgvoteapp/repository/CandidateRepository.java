package com.orgvoteapp.repository;

import com.orgvoteapp.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {}
