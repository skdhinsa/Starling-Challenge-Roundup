package com.example.serenakd.roundup.service;


public interface RoundUpService {
    int roundUpWeeklyTransactionsAmount() throws Exception;
    void sweepAmountIntoSavingsGoal() throws Exception;
    String getSavingsGoalUid();
}
