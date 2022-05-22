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
     * @return
     */
    @Override
    public void sweepAmountIntoSavingsGoal() throws Exception {
        int sweepingAmount = roundUpWeeklyTransactionsAmount();
        savingsGoalService.addToSavingsGoal(getSavingsGoalUid(), sweepingAmount);
    }

    @Override
    public int roundUpWeeklyTransactionsAmount() throws Exception {
        try{
            return roundUpTotal.calculate(transactionsBetweenService.getTransactionsBetweenDates());
        } catch (Exception e) {
            log.error("Error: In round-up transaction ", e);
            throw new Exception(e.getCause());
        }

    }

    @Override
    public String getSavingsGoalUid() {
        if(!savingsGoalService.isGoalAlreadyPresent()){
            log.info("Creating new Savings Goal");
            return savingsGoalService.createNewSavingsGoal();
        }
        log.info("Retrieving SavingsGoalUid");
        return savingsGoalService.getRoundUpSavingsGoalUid();
    }

}
