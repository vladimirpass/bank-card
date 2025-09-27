package com.example.bankcard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionDto {
    private Integer fromCardOneId;
    private Integer toCardTwoId;
    private Integer value;
}
