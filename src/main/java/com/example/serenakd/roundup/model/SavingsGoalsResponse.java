package com.example.serenakd.roundup.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class SavingsGoalsResponse {

    private List<SavingsGoals> savingsGoalList;
}
