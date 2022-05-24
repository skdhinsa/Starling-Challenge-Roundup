package com.example.serenakd.roundup.service.Impl;

import com.example.serenakd.roundup.service.RoundUpService;
import com.example.serenakd.roundup.util.RoundUpTotal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoundUpServiceImpl implements RoundUpService {

    private final TransactionsBetweenServiceImpl transactionsBetweenService;

    private final SavingsGoalServiceImpl savingsGoalService;

    private final RoundUpTotal roundUpTotal;

    @Autowired
    public RoundUpServiceImpl(TransactionsBetweenServiceImpl transactionsBetweenService, SavingsGoalServiceImpl savingsGoalService, RoundUpTotal roundUpTotal) {
        this.transactionsBetweenService = transactionsBetweenService;
        this.savingsGoalService = savingsGoalService;
        this.roundUpTotal = roundUpTotal;
    }

    /**
     * Sweep weekly round up amount into SavingsGoal
     */
    @Override
    public void sweepRoundedAmountIntoSavingsGoal(String accountUid, String minTransactionTimestamp, String maxTransactionTimestamp) throws Exception {
        savingsGoalService.addToSavingsGoal(accountUid, getSavingsGoalUid(), roundUpTransactionsForWeek(minTransactionTimestamp, maxTransactionTimestamp));
    }

    /**
     * Calculate the round up amount for weekly transactions.
     * @return  rounded up amount to sweep for transaction in a week
     */
    @Override
    public int roundUpTransactionsForWeek(String minTransactionTimestamp, String maxTransactionTimestamp) throws Exception {
        try{
            return roundUpTotal.calculate(
                    transactionsBetweenService.getTransactionsBetweenDates(minTransactionTimestamp, maxTransactionTimestamp));
        } catch (Exception e) {
            log.error("Error: In round-up transaction ", e);
            throw new Exception(e.getCause());
        }
    }

    /**
     * Retrieve the savingsGoalUid - for newly created goal or existing
     * @return  savingsGoalUid
     */
    @Override
    public String getSavingsGoalUid() {
        if(!savingsGoalService.isGoalAlreadyPresent()){
            log.info("Creating new Savings Goal");
            return savingsGoalService.createNewSavingsGoalUid();
        }
        log.info("Retrieving SavingsGoalUid");
        return savingsGoalService.getRoundUpSavingsGoalUid();
    }

}
