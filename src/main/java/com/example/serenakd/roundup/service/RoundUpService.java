package com.example.serenakd.roundup.service;


public interface RoundUpService {
    int roundedUpWeeklyTransactionsAmount() throws Exception;
    public void sweepRoundedAmountIntoSavingsGoal(String accountUid) throws Exception ;
    String getSavingsGoalUid();
}
