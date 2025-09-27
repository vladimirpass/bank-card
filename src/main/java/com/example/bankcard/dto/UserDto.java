package com.example.bankcard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String nameUser;
    private List<CardDto> cards = List.of();
}
