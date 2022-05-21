package com.example.serenakd.roundup.model;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class SavingsGoalRequest {

    private String name;
    private String currency;
    private Amount target;


    public SavingsGoalRequest(String name, String currency, Amount target) {
        this.name = name;
        this.currency = currency;
        this.target = target;
    }
}
