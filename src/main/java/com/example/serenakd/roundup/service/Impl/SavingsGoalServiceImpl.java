package com.example.serenakd.roundup.service.Impl;

import com.example.serenakd.roundup.model.Amount;
import com.example.serenakd.roundup.model.SavingsGoals;
import com.example.serenakd.roundup.service.SavingsGoalService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    private final Environment environment;
    private final RestTemplate restTemplate;
    private final AccountServiceImpl accountService;
    private final HttpHeaders httpHeaders = new HttpHeaders();

    public String getBearerToken() {
        return environment.getProperty("bearerToken");
    }

    @Autowired
    public SavingsGoalServiceImpl(RestTemplate restTemplate, AccountServiceImpl accountService, Environment environment) {
        this.restTemplate = restTemplate;
        this.accountService = accountService;
        this.environment = environment;
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
     *  @param accountUid - the account to perform the Round-up
     *  @param savingsGoalUid - the Uid for Round-up Savings Goal
     *  @param sweepingAmount - amount to sweep into Savings Goal
     */
    @Override
    public void addToSavingsGoal(String accountUid, String savingsGoalUid, int sweepingAmount) {
        final String transferUid = UUID.randomUUID().toString();
        String urlTemplate = String.format("https://api-sandbox.starlingbank.com/api/v2/account/%s/savings-goals/%s/add-money/%s", accountUid, savingsGoalUid,transferUid);
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
        ResponseEntity<SavingsGoalsResponse> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, createHttpHeaders(getBearerToken(), httpHeaders), SavingsGoalsResponse.class);
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
        httpHeaders.set(AUTHORIZATION, format("Bearer %s", getBearerToken()));
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
