package com.example.bankcard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name_user")
    private String nameUser;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Card> cards;

}
