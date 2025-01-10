package com.sahajdeepsingh.onetabread.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "urihistories")
public class URIHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hist_id")
    private Long id;

    @Setter
    @Column(name = "uri")
    private String uri;

    @Setter
    @Column(name = "visitedAt")
    private LocalDateTime visitedAt;

    @ManyToOne
    @JoinColumn(name = "book")
    private Book book;
}
