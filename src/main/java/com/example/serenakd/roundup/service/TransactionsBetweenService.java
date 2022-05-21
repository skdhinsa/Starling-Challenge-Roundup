package com.example.serenakd.roundup.service;

import com.example.serenakd.roundup.model.Account;

import java.util.List;

public interface TransactionsBetweenService {
    List<Integer> callGetTransactions(AccountService accountService);
}
