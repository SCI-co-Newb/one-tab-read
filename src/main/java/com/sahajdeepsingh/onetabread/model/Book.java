package com.sahajdeepsingh.onetabread.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "book", targetEntity = URIPattern.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private URIPattern pattern;

    @OneToMany(mappedBy = "book", targetEntity = URIHistory.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("visitedAt DESC")
    private List<URIHistory> history = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public URIPattern getPattern() {
        return pattern;
    }

    public void setPattern(URIPattern pattern) {
        this.pattern = pattern;
    }

    public List<URIHistory> getHistory() {
        return history;
    }

    public void setHistory(List<URIHistory> history) {
        this.history = history;
    }
}
