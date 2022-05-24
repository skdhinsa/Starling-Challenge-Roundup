package com.example.serenakd.roundup.service.Impl;

import com.example.serenakd.roundup.model.Amount;
import com.example.serenakd.roundup.service.AccountService;
import com.example.serenakd.roundup.service.TransactionsBetweenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public TransactionsBetweenServiceImpl(RestTemplate restTemplate, AccountService accountService) {
        this.restTemplate = restTemplate;
        this.accountService = accountService;
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

        String bearerToken = "eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31Uy5LbIBD8lZTOy5belnTLLT-QDxhgsCkjUAHazVYq_x4kkCU5rtzc3fPoGUb-nUnnsiGDSRKOo3l3HqyS-kpB39-ZGbO3zM00RPCW1b241KTpmprUZYOku5QlQUY7ijQvy6IJwfhryoaibaqqz4umfMsk-ETk1WUhgDEza__DKI72p-ShNuYlzauGBwuYk5pjS6hgFakFZW1XiKJjfajtzR11zLjUoq3zuiBNU3JSt6IgUEJNWlbknF76qushZISxvjOGzsWshvZ5fuGMUI6C1FW3_Co4oU3Z1bTltC-WPo6ZCZelRKeEKeOQDxaBf9u422qfaBjxpeC_pidBctReCon2zCvp_IlJgHMbjA_IpX-AqHgP7DbiI3LHn1Z6_AazvxkrXXhGIjWXH5LPoGIwBQWaJWsMLCfMaG-Nio0WJmlGC2lH8NJoYgQRs-buIblH9w3E1mx23ozbiDiCTIUVBiP6OsA0qa8HWqNG0Bw8DhwVhhIbTJq9o18GmSwKtBi8u_9J0UbUJgUMwwY8Xu06xzHxXzGlomU32KYb0UNwAwMLcFUTXoea4AtxkyJIQ0SwBxE5wjXNlIjQ2ertWWL8QfYWtAO2uw40obO6D9vr4k7tDiLeTUS8FVhuJNzbKP1eUxkWTBwqrAQxy5E8synLGiHVNlKc8UStURYZysmfgDtLceEOPsIjOnI1u48Tl6Y5cWudIxMXFs7gVYldfFFrF2NRdkM-K-QkLSzR6H0YcJ4SnGD7iMI_5nrMxFh-aH9mt75n9kU-MZ_6wXtcX4y5j2dq4iJRM3XMhm0uh7J1OXJr1PGa1gd7Pq_sz1-aU6EMCgYAAA.GnQDHarYQWGognDtbPAv5bWLCB0C3bVc7iZ0TsDqgSlzw_gWNqyyQpiO2y3FOjx9zQSh7uxBzBxFl5s71n64R_w0_TLrShy0O3tZyr0STN-ERbFzrVeJNEeqmJAugC2aqrw_RHiRYi50koadKYISDit6GiZ7AQau9Wqvkqyf0EUf7BILUNVbB__v7uhZZo7jG-Hjj-l4ZMKNu8yanAEIUfuGyewZFtla_cF2JEwVO2ZDfw2phOLlTw0L_1nQt6doWcQSXXWR2pIRcYiK4b7laWu4evKkKH1-xlJufM35KXEfUO45yTjsD5leDEiSzRjqzddBYiJEiMbxbJNVlbaSQjZVwjtXwwP_BC_MEgrZblbpvIdBZuidBsfWTw6At0TRhWwmA22y9GrYATkUYoqKMMkkF0mRmCLXq_rQluE5Z6vkmbnAXzYf1DqpKTi1b7NZTNFirpoej9ghFiBbtNRO5rxC5T-bcKiYnxLphrll8QK2859jGCTwMPFfRBxxzbs5XGJidi80P1354mLD72d291SUsT7IFfnl8FtGEYFrn_0giW-zKJlu4cgP-VX9sKmllA8iyP6EHKC5y3lPifYA83Y8IzMyvFuWkeNo8f-IUvZc9UBClyBrZPTeRuBZbID4sGOKYh1tEcldhhxmVy0frQiBtFAj53rOyaWqwd38GX4";

        ResponseEntity<TransactionResponse> response = restTemplate.exchange(urlTemplate, HttpMethod.GET,
                createHttpHeaders(bearerToken, httpHeaders), TransactionResponse.class);
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
