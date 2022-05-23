package com.example.serenakd.roundup.service;


public interface RoundUpService {
    int roundUpTransactionsForWeek(String minTransactionTimestamp, String maxTransactionTimestamp) throws Exception;
    void sweepRoundedAmountIntoSavingsGoal(String accountUid, String minTransactionTimestamp, String maxTransactionTimestamp) throws Exception;
    String getSavingsGoalUid();
}
