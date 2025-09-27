package com.example.bankcard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "number_card")
    private long numberCard;

    @Column(name = "time_work")
    private int timeWork;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "balance")
    private double balance;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User users;

    public enum StatusEnum {
        ACTIVE,BLOCK,FINISHTIME;
    }

}


