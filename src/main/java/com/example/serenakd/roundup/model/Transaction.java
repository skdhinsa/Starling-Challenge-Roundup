package com.example.serenakd.roundup.model;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Transaction {
    private String feedItemUid;
    private Amount amount;
    private String updatedAt;
    private String transactionTime;
}
