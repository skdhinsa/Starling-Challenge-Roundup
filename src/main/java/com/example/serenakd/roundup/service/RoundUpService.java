package com.example.serenakd.roundup.service;


public interface RoundUpService {
    int roundedUpWeeklyTransactionsAmount() throws Exception;
    void sweepAmountIntoSavingsGoal() throws Exception;
    String getSavingsGoalUid();
}
