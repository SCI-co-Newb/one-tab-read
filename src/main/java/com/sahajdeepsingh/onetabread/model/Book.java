package com.sahajdeepsingh.onetabread.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Setter
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @OneToOne(mappedBy = "book", targetEntity = URIPattern.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private URIPattern pattern;

    @OneToMany(mappedBy = "book", targetEntity = URIHistory.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("visitedAt DESC")
    private List<URIHistory> history = new ArrayList<>();
}
