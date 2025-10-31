package com.orgvoteapp.model;

import jakarta.persistence.*;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orgName;
    private String email;
    private String password;

    public Admin() {}

    public Admin(String orgName, String email, String password) {
        this.orgName = orgName;
        this.email = email;
        this.password = password;
    }

    public Long getId() { return id; }
    public String getOrgName() { return orgName; }
    public void setOrgName(String orgName) { this.orgName = orgName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
