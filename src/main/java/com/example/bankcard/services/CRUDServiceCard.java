package com.example.bankcard.services;

import com.example.bankcard.dto.CardDto;
import com.example.bankcard.dto.TransactionDto;

import java.util.Collection;

public interface CRUDServiceCard<T> {
    T getById(Integer id);
    Collection<T> getAll();
    CardDto create(T item);
    CardDto update(Integer id, T item);
    void deleteById(Integer id);
    CardDto blockCard(Integer id);
    Double getBalanceCard(Integer id);
    void makeCallBlock(Integer id, String callBlock);
    void transaction(TransactionDto transactionDto);
}
