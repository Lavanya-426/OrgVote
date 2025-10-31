package com.orgvoteapp.model;

import javax.persistence.*;

@Entity
public class Election {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean active = false;

    public Election() {}
    public Election(String title, String description){this.title=title;this.description=description;}
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getTitle(){return title;}
    public void setTitle(String t){this.title=t;}
    public String getDescription(){return description;}
    public void setDescription(String d){this.description=d;}
    public boolean isActive(){return active;}
    public void setActive(boolean a){this.active=a;}
}
