package com.example.serenakd.roundup.util;

import org.springframework.stereotype.Component;

@Component
public class RestAPIs {

    public static final String GET_ACCOUNT_INFO_API = "https://api-sandbox.starlingbank.com/api/v2/accounts";
    public static final String GET_TRANSACTIONS_BETWEEN_DATES_API = "https://api-sandbox.starlingbank.com/api/v2/feed/account/%s/category/%s/transactions-between?minTransactionTimestamp=%s&maxTransactionTimestamp=%s";
    public static final String GET_ALL_SAVINGS_GOALS_API = "https://api-sandbox.starlingbank.com/api/v2/account/%s/savings-goals";
    public static final String PUT_SAVINGS_GOAL_API = "https://api-sandbox.starlingbank.com/api/v2/account/%s/savings-goals";
    public static final String ADD_TO_SAVINGS_GOAL_API = "https://api-sandbox.starlingbank.com/api/v2/account/%s/savings-goals/%s/add-money/%s";


}
