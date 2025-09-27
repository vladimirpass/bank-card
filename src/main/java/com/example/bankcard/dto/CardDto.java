package com.example.bankcard.dto;

import com.example.bankcard.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardDto {
    private Integer id;
    private String name;
    private long numberCard;
    private int timeWork;
    private Card.StatusEnum status;
    private double balance;

    private Integer userId;

}
