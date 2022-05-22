package com.example.serenakd.roundup.service.Impl;

import com.example.serenakd.roundup.service.RoundUpService;
import com.example.serenakd.roundup.util.RoundUpTotal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoundUpServiceImpl implements RoundUpService {

//    @Value("${bearerToken}")
//    private String bearerToken;

    private static final Logger logger = LoggerFactory.getLogger(RoundUpService.class);

    @Autowired
    private TransactionsBetweenServiceImpl transactionsBetweenService;

    @Autowired
    private SavingsGoalServiceImpl savingsGoalService;

    @Autowired
    private RoundUpTotal roundUpTotal;
    /**
     * @return
     */
    @Override
    public int putSweepAmountIntoSavingsGoal() throws Exception {
        int sweepingAmount = roundUpTransactions();
        String savingsGoalUid = getSavingsGoalUid();
        savingsGoalService.addToSavingsGoal(sweepingAmount, savingsGoalUid);
        return 0;
    }

    @Override
    public int roundUpTransactions() throws Exception {
        try{
            List<Integer> weeklyTransactions = transactionsBetweenService.getTransactionsBetweenDates();
            return roundUpTotal.calculate(weeklyTransactions);
        } catch (Exception e) {
            logger.error("Error: In round-up transaction ", e);
            throw new Exception(e.getCause());
        }

    }

    @Override
    public String getSavingsGoalUid() {
        String savingsGoalUid;
        Boolean isRoundupGoalPresent = savingsGoalService.isGoalAlreadyPresent();
        if(!isRoundupGoalPresent){
            logger.info("Creating new Savings Goal");
            savingsGoalUid = savingsGoalService.createNewSavingsGoal();
        }
        savingsGoalUid = savingsGoalService.getRoundUpSavingsGoalUid();
        logger.info("Retrieving SavingsGoalUid");
        return savingsGoalUid;
    }

}
