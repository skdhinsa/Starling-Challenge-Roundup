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

    private final String bearerToken = "eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31Uy5KbMBD8lRTn1ZbBYGNuueUH8gHSzMhWWUiUJHazlcq_RyBhwHHl5u6eR89o8O9CeV90BR8UQ-rtuw_caWWugpv7O9i-eCv8KGIEnqC-yHPNmrapWV01xNpzVTEC0QoSh6oqmxhMv4aiK0_NsSmPx_LwVigeElFXp8tEcAA7mvDDaiT3U2GsTYdKHI4NRgt0YDXSiQkJR1ZLAae2lGULl1g72DuZlHEGKVAgMBTnktUnbNillJIdy9ijAXE-z27iWN8ByPuU1YjL4XCOWQJJsvrYTr9KZKKp2lqcUFzKqY8HO9C0lOSUgbaesHPE8dvC3Wb7zPCeXgrha3gSFJIJSipye14rH3ZMBoguGu8IVXiApITA4dbTI3LFn04F-sbHcLNO-fiMTBlUHwpHrlOw4JobyNaAO2RgTXBWp0YTkzVrpHI9D8oaZiWTo0H_kPyj-wJSaxh9sP0yIvVc5cKaohFz7fgw6K8HmqN6bpAH6pA0xRILzJq7U5gGGRxJchS9-_9JyUbSBs2B4gYCXd08xzbxXzGnkoMbX6brKfDohncQ4axmPA818C-iRUogD5HAGsRUz695pkzEzs4sz5LiN3Jw3HgOq-tIMzHqe7e8Lq3U6iDh1UTCS4HpRuK99SqsNbWFaGJTYSaYnY7kmc1Zzkqll5HSjDtqjnIEpIawA34vpYV7_hEf0bOrXX3suDzNjpvrbJm0sHgGr0qs4otaq5iKwo1w1IQsLyzTFEIccBwyHPjyEcV_zPmYmXW4ab9nl7579kU-s5_mwQeaXwz8xzM1oMzUKDy4uM3pUJYuW26O2l7T_GDP51X8-Qvl5TR7CgYAAA.wL8UiIlx_t1JKGpmKjQFM9ReRQZD2Ftm-2SP2k1arZJhIznZ5qEmUq4uKVn1GdL88R96fMe05MNhk-MWwePCfhar1yBg2U7io4WUxbLa1RVyZ2jZU9DBXk6dwrmj_plIx0jd-6KsugHyh7Uj5MR3UF7iFKTw0-GiYPCAkos3Ro7FjLnGfIg2NbGyeUgZ4Ss7ykoo65GXLpDP52qzA4WBGUFfBiar8Zn9Nwr0pDvWdrN3G2kb5q54DbXOM_4gpuW9QnABaqWEPG156rBHDfLzYgMO4s8fcEIqsyGxgaRkaiNQOq_c6drA8cnnxaAaXRFMRjiQ3HeqH2bt5JY5gf5wSUSn0ow0UWc7qSwl9FgZbSBhlJcJSi-YVNz_Lo2b-1DcberL-jbdhvLBWgWS2YDvIwtHUc9ZIRLVmDQcrVsaGkqJHks179nO7Mf7CXASKJS0tPsBI0nlGSSlkCs_twYNciMziJz_mNm75eRvcAwVzoRevnGh5yBDvdLjCjoCR1T4f_ZR-AcboE4HmR7BzJ_8etkcy9BHJ6r28O32A8w2mwNoVw1wb8FGxRfA_JlHdMfEaKYLGotq027LmBdXOldcI_WSVlfl1l6SLfokN_VvU_mbL78U59xq8koOaKPjrPT7H_2Tl7zhvxqxJc5xPgLT09FJwVRbCov1__73MI1Rkno";

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
