package com.example.serenakd.roundup.service;


public interface RoundUpService {
    void sweepRoundedAmountIntoSavingsGoal(String accountUid, String minTransactionTimestamp, String maxTransactionTimestamp) throws Exception;
    int roundUpTransactionsForWeek(String minTransactionTimestamp, String maxTransactionTimestamp) throws Exception;
    String getSavingsGoalUid();
}
