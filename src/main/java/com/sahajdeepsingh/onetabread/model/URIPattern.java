package com.sahajdeepsingh.onetabread.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "uripatterns")
public class URIPattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patt_id")
    private Long id;

    @Setter
    @Column(name = "pattern")
    private String pattern;

    @OneToOne
    @JoinColumn(name = "book")
    private Book book;
}
