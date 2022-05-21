package com.example.serenakd.roundup.service;


import com.example.serenakd.roundup.model.Transaction;

import java.util.List;

public interface RoundUpService {
    int roundUpTransactions() throws Exception;
    int putSweepAmountIntoSavingsGoal() throws Exception;
    String getSavingsGoalUid() ;
}
