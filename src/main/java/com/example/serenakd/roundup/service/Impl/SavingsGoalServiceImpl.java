package com.example.serenakd.roundup.service.Impl;

import com.example.serenakd.roundup.model.Amount;
import com.example.serenakd.roundup.model.SavingsGoals;
import com.example.serenakd.roundup.service.SavingsGoalService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.serenakd.roundup.util.Utilities.createHttpHeaders;
import static java.lang.String.format;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
@Service
@Slf4j
public class SavingsGoalServiceImpl implements SavingsGoalService {

    private final String bearerToken = "eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31Uy5LbIBD8lZTOy5belnTLLT-QDxhgsCkjUAHazVYq_x4kkCU5rtzc3fPoGUb-nUnnsiGDSRKOo3l3HqyS-kpB39-ZGbO3zM00RPCW1b241KTpmprUZYOku5QlQUY7ijQvy6IJwfhryoaibaqqz4umfMsk-ETk1WUhgDEza__DKI72p-ShNuYlzauGBwuYk5pjS6hgFakFZW1XiKJjfajtzR11zLjUoq3zuiBNU3JSt6IgUEJNWlbknF76qushZISxvjOGzsWshvZ5fuGMUI6C1FW3_Co4oU3Z1bTltC-WPo6ZCZelRKeEKeOQDxaBf9u422qfaBjxpeC_pidBctReCon2zCvp_IlJgHMbjA_IpX-AqHgP7DbiI3LHn1Z6_AazvxkrXXhGIjWXH5LPoGIwBQWaJWsMLCfMaG-Nio0WJmlGC2lH8NJoYgQRs-buIblH9w3E1mx23ozbiDiCTIUVBiP6OsA0qa8HWqNG0Bw8DhwVhhIbTJq9o18GmSwKtBi8u_9J0UbUJgUMwwY8Xu06xzHxXzGlomU32KYb0UNwAwMLcFUTXoea4AtxkyJIQ0SwBxE5wjXNlIjQ2ertWWL8QfYWtAO2uw40obO6D9vr4k7tDiLeTUS8FVhuJNzbKP1eUxkWTBwqrAQxy5E8synLGiHVNlKc8UStURYZysmfgDtLceEOPsIjOnI1u48Tl6Y5cWudIxMXFs7gVYldfFFrF2NRdkM-K-QkLSzR6H0YcJ4SnGD7iMI_5nrMxFh-aH9mt75n9kU-MZ_6wXtcX4y5j2dq4iJRM3XMhm0uh7J1OXJr1PGa1gd7Pq_sz1-aU6EMCgYAAA.GnQDHarYQWGognDtbPAv5bWLCB0C3bVc7iZ0TsDqgSlzw_gWNqyyQpiO2y3FOjx9zQSh7uxBzBxFl5s71n64R_w0_TLrShy0O3tZyr0STN-ERbFzrVeJNEeqmJAugC2aqrw_RHiRYi50koadKYISDit6GiZ7AQau9Wqvkqyf0EUf7BILUNVbB__v7uhZZo7jG-Hjj-l4ZMKNu8yanAEIUfuGyewZFtla_cF2JEwVO2ZDfw2phOLlTw0L_1nQt6doWcQSXXWR2pIRcYiK4b7laWu4evKkKH1-xlJufM35KXEfUO45yTjsD5leDEiSzRjqzddBYiJEiMbxbJNVlbaSQjZVwjtXwwP_BC_MEgrZblbpvIdBZuidBsfWTw6At0TRhWwmA22y9GrYATkUYoqKMMkkF0mRmCLXq_rQluE5Z6vkmbnAXzYf1DqpKTi1b7NZTNFirpoej9ghFiBbtNRO5rxC5T-bcKiYnxLphrll8QK2859jGCTwMPFfRBxxzbs5XGJidi80P1354mLD72d291SUsT7IFfnl8FtGEYFrn_0giW-zKJlu4cgP-VX9sKmllA8iyP6EHKC5y3lPifYA83Y8IzMyvFuWkeNo8f-IUvZc9UBClyBrZPTeRuBZbID4sGOKYh1tEcldhhxmVy0frQiBtFAj53rOyaWqwd38GX4";

    private final RestTemplate restTemplate;

    private final AccountServiceImpl accountService;

    private final HttpHeaders httpHeaders = new HttpHeaders();

    @Autowired
    public SavingsGoalServiceImpl(RestTemplate restTemplate, AccountServiceImpl accountService) {
        this.restTemplate = restTemplate;
        this.accountService = accountService;
    }

    /**
     * @return savingsGoalUid
     */
    @Override
    public String createNewSavingsGoalUid() {
        String urlTemplate = String.format("https://api-sandbox.starlingbank.com/api/v2/account/%s/savings-goals", accountService.getAccounts().accountUid());
        createHTTPHeadersWithBody();

        Amount target = new Amount();
        target.setCurrency("GBP");
        target.setMinorUnits(10000);
        SavingsGoalRequest body = new SavingsGoalRequest("Round-up Savings", "GBP", target);

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpEntity<SavingsGoalRequest> requestEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<SavingGoalResponse> response = restTemplate.exchange(urlTemplate, HttpMethod.PUT, requestEntity, SavingGoalResponse.class);

        return Objects.requireNonNull(response.getBody()).savingsGoalUid();
    }

    /**
     *  Take the given savingsGoalUid and sweep amount into it.
     *  @param savingsGoalUid - the Uid for Round-up Savings Goal
     *  @param sweepingAmount - amount to sweep into Savings Goal
     */
    @Override

    public void addToSavingsGoal(String savingsGoalUid, int sweepingAmount) {
        final String transferUid = UUID.randomUUID().toString();
        String urlTemplate = String.format("https://api-sandbox.starlingbank.com/api/v2/account/%s/savings-goals/%s/add-money/%s", accountService.getAccounts().accountUid(), savingsGoalUid,transferUid);
        createHTTPHeadersWithBody();

        Amount transfer = new Amount();
        transfer.setCurrency("GBP");
        transfer.setMinorUnits(sweepingAmount);
        SavingsGoalTransferRequest requestBody = new SavingsGoalTransferRequest(transfer);

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpEntity<SavingsGoalTransferRequest> requestEntity = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<SavingGoalTransferResponse> response = restTemplate.exchange(urlTemplate, HttpMethod.PUT, requestEntity, SavingGoalTransferResponse.class);

        Objects.requireNonNull(response.getStatusCode());
    }

    /**
     * @return savingsGoalList
     */
    @Override
    public List<SavingsGoals> getAllSavingsGoals() {
        String urlTemplate = String.format("https://api-sandbox.starlingbank.com/api/v2/account/%s/savings-goals", accountService.getAccounts().accountUid());
        ResponseEntity<SavingsGoalsResponse> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, createHttpHeaders(bearerToken, httpHeaders), SavingsGoalsResponse.class);
        log.info("Retrieving all savings goals");
        return Objects.requireNonNull(response.getBody()).savingsGoalList();

    }

    /**
     * Check if there already exists a 'Round-up' savings goal
     * @return boolean
     */
    @Override
    public boolean isGoalAlreadyPresent(){
        List<SavingsGoals> savingsGoals = getAllSavingsGoals();
        for (SavingsGoals savingsGoal: savingsGoals) {
            String goalName = savingsGoal.name();
            if(goalName.equals("Round-up Savings")){
                log.info("'Round-up Savings goal' exists of {}", goalName);
                return true;
            }
        }
        return false;
    }

    /**
     * If there already exists a 'Round-up' savings goal, get the savingsGoalUid
     * @return savingsGoalUid or null
     */
    @Override
    public String getRoundUpSavingsGoalUid(){
        List<SavingsGoals> savingsGoals = getAllSavingsGoals();
        for (SavingsGoals savingsGoal: savingsGoals) {
            String goalName = savingsGoal.name();
            if(goalName.equals("Round-up Savings")){
                log.info("Retrieving 'Round-up Savings' Uid");
                return savingsGoal.savingsGoalUid();
            }
        }
        return null;
    }

    public record SavingGoalResponse(String savingsGoalUid, Boolean success) {
    }

    public record SavingGoalTransferResponse(String transferUid, Boolean success) {

    }

    public record SavingsGoalsResponse(List<SavingsGoals> savingsGoalList) {

    }

    public record SavingsGoalRequest(String name, String currency, Amount target) {

    }

    private void createHTTPHeadersWithBody() {
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(AUTHORIZATION, format("Bearer %s", bearerToken));
    }

    @ToString
    @Getter
    @Setter
    public static class SavingsGoalTransferRequest {
        private Amount amount;

        public SavingsGoalTransferRequest(Amount amount) {
            this.amount = amount;
        }

    }
}
