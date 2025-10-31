package com.orgvoteapp.model;

import jakarta.persistence.*;

@Entity
public class Voter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String voterId;
    private String name;
    private String email;
    private boolean hasVoted = false;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    public Voter() {}

    public Voter(String voterId, String name, String email) {
        this.voterId = voterId;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getVoterId() { return voterId; }
    public void setVoterId(String voterId) { this.voterId = voterId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isHasVoted() { return hasVoted; }
    public void setHasVoted(boolean hasVoted) { this.hasVoted = hasVoted; }
    public Election getElection() { return election; }
    public void setElection(Election election) { this.election = election; }
}
