package com.example.serenakd.roundup.service.Impl;

import com.example.serenakd.roundup.model.Account;
import com.example.serenakd.roundup.model.TransactionResponse;
import com.example.serenakd.roundup.service.AccountService;
import com.example.serenakd.roundup.service.TransactionsBetweenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class TransactionsBetweenServiceImpl implements TransactionsBetweenService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionsBetweenService.class);

    @Value("${bearerToken}")
    private String bearerToken;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccountService accountService;

    /**
     * @param accountService
     * @return List of transactions between min and max timestamp
     */
    @Override
    public List<Integer> callGetTransactions(AccountService accountService){
        String accountUid = accountService.callGetAccountInfoAPI().getAccountUid();
        String categoryUid = accountService.callGetAccountInfoAPI().getDefaultCategory();
        String urlTemplate = String.format("https://api-sandbox.starlingbank.com/api/v2/feed/account/%s/category/%s/transactions-between?minTransactionTimestamp=2022-05-12T00:00:00.000Z&maxTransactionTimestamp=2022-05-19T23:59:00.000Z",
                accountUid, categoryUid);

        HttpHeaders httpsHeaders = new HttpHeaders();
        httpsHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpsHeaders.set(AUTHORIZATION, bearerToken);
        HttpEntity<String> entity = new HttpEntity<>(httpsHeaders);

        restTemplate = new RestTemplate();

        ResponseEntity<TransactionResponse> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, TransactionResponse.class);

        return Objects.requireNonNull(response.getBody()).getFeedItems().stream().map(t -> t.getAmount().getMinorUnits()).toList();
    }
}
