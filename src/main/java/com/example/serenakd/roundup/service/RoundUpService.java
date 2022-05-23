package com.example.serenakd.roundup.service;


public interface RoundUpService {
    int roundedUpWeeklyTransactionsAmount() throws Exception;
    void sweepAmountIntoSavingsGoal(String accountUid) throws Exception;
    String getSavingsGoalUid();
}
