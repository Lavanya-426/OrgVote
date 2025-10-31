package com.orgvoteapp.repository;

import com.orgvoteapp.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election, Long> {}
