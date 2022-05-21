package com.example.serenakd.roundup.service;


import com.example.serenakd.roundup.model.SavingsGoals;
import com.example.serenakd.roundup.model.SavingsGoalsResponse;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface SavingsGoalService {
    String createNewSavingsGoal();
    HttpStatus addToSavingsGoal(int sweepingAmount, String savingsGoalUid);
    List<SavingsGoals> getAllSavingsGoals();
    boolean isGoalAlreadyPresent();
    String getRoundUpSavingsGoalUid();
}
