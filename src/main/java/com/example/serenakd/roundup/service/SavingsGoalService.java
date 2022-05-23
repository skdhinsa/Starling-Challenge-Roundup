package com.example.serenakd.roundup.service;


import com.example.serenakd.roundup.model.SavingsGoals;

import java.util.List;

public interface SavingsGoalService{
    String createNewSavingsGoalUid();
    void addToSavingsGoal(String savingsGoalUid, int sweepingAmount);
    List<SavingsGoals> getAllSavingsGoals();
    boolean isGoalAlreadyPresent();
    String getRoundUpSavingsGoalUid();
}
