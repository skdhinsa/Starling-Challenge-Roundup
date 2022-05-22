package com.example.serenakd.roundup.service;


import com.example.serenakd.roundup.model.Transaction;

import java.util.List;

public interface RoundUpService {
    int roundUpWeeklyTransactionsAmount() throws Exception;
    int sweepAmountIntoSavingsGoal() throws Exception;
    String getSavingsGoalUid() ;
}
