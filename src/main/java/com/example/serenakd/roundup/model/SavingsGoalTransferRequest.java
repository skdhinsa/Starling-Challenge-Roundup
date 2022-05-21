package com.example.serenakd.roundup.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.List;

@ToString
@Getter
@Setter
public class SavingsGoalTransferRequest {

    private Amount amount;


    public SavingsGoalTransferRequest(Amount amount) {
        this.amount = amount;
    }
}
