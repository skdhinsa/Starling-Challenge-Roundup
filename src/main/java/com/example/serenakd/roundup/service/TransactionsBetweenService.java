package com.example.serenakd.roundup.service;


import java.util.List;

public interface TransactionsBetweenService {
    List<Integer> getTransactionsBetweenDates(String minTransactionTimestamp, String maxTransactionTimestamp);
}
