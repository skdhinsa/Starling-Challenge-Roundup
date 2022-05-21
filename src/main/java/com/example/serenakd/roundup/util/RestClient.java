package com.example.serenakd.roundup.util;

import com.example.serenakd.roundup.model.SavingGoalTransferResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RestClient {

    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);
    public static final String GET_ACCOUNT_INFO_API = "https://api-sandbox.starlingbank.com/api/v2/accounts";
    public static final String GET_TRANSACTIONS_BETWEEN_DATES_API = "https://api-sandbox.starlingbank.com/api/v2/feed/account/%s/category/%s/transactions-between";
//    public static final String GET_TRANSACTIONS_BETWEEN_DATES_API = "https://api-sandbox.starlingbank.com/api/v2/feed/account/{accountUid}/category/{categoryUid}/transactions-between";
    public static final String GET_ALL_SAVINGS_GOALS_API = "https://api-sandbox.starlingbank.com/api/v2/account/{accountUid}/savings-goals";
    public static final String PUT_SAVINGS_GOAL_API = "https://api-sandbox.starlingbank.com/api/v2/account/{accountUid}/savings-goals";
    public static final String ADD_TO_SAVINGS_GOAL_API = "https://api-sandbox.starlingbank.com/api/v2/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}";

}
