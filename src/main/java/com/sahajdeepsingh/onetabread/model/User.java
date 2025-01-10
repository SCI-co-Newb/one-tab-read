package com.sahajdeepsingh.onetabread.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Setter
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Setter
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", targetEntity = Book.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();
}
