package com.example.serenakd.roundup.service.Impl;

import com.example.serenakd.roundup.model.Amount;
import com.example.serenakd.roundup.service.AccountService;
import com.example.serenakd.roundup.service.TransactionsBetweenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static com.example.serenakd.roundup.util.Utilities.createHttpHeaders;

@Service
@Slf4j
public class TransactionsBetweenServiceImpl implements TransactionsBetweenService {

    private final RestTemplate restTemplate;
    private final AccountService accountService;
    private final HttpHeaders httpHeaders  = new HttpHeaders();
    private final Environment environment;

    public String getBearerToken() {
        return environment.getProperty("bearerToken");
    }

    @Autowired
    public TransactionsBetweenServiceImpl(RestTemplate restTemplate, AccountService accountService, Environment environment) {
        this.restTemplate = restTemplate;
        this.accountService = accountService;
        this.environment = environment;
    }

    /**
     * @return List of transactions between min and max timestamp
     */
    @Override
    public List<Integer> getTransactionsBetweenDates(String minTransactionTimestamp, String maxTransactionTimestamp){
        String urlTemplate = String.format("https://api-sandbox.starlingbank.com/api/v2/feed/account/%s/category/%s/transactions-between?minTransactionTimestamp=%s&maxTransactionTimestamp=%s",
                accountService.getAccounts().accountUid(),
                accountService.getAccounts().defaultCategory(),
                minTransactionTimestamp,
                maxTransactionTimestamp);

        ResponseEntity<TransactionResponse> response = restTemplate.exchange(urlTemplate, HttpMethod.GET,
                createHttpHeaders(getBearerToken(), httpHeaders), TransactionResponse.class);
        log.info("Retrieved list of outgoing transactions in given week");
        return Objects.requireNonNull(response.getBody()).feedItems().stream()
                .filter(item -> item.direction.equals("OUT"))
                .map(transaction -> transaction.amount().getMinorUnits())
                .toList();
    }


    public record TransactionResponse(List<Transaction> feedItems) {
    }

    public record Transaction(String feedItemUid, Amount amount, String updatedAt, String transactionTime, String direction) {
    }
}
