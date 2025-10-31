package com.orgvoteapp.model;

import jakarta.persistence.*;

@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String candidateId;
    private String name;
    private String party;
    private String symbol;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    public Candidate() {}

    public Candidate(String candidateId, String name, String party, String symbol) {
        this.candidateId = candidateId;
        this.name = name;
        this.party = party;
        this.symbol = symbol;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getCandidateId() { return candidateId; }
    public void setCandidateId(String candidateId) { this.candidateId = candidateId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getParty() { return party; }
    public void setParty(String party) { this.party = party; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public Election getElection() { return election; }
    public void setElection(Election election) { this.election = election; }
}
