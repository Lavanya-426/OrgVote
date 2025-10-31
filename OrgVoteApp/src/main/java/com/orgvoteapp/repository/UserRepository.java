package com.orgvoteapp.repository;

import com.orgvoteapp.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Voter, Long> {}
