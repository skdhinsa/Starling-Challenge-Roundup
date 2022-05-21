package com.example.serenakd.roundup.util;

import com.example.serenakd.roundup.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class roundUpTotal {

    private static final Logger logger = LoggerFactory.getLogger(roundUpTotal.class);

    /**
     * Calculate the total rounded up.
     * @param transactions the list of transactions
     * @return none
     */
    public void calculate(final List<Transaction> transactions)  {
        for (final Transaction transaction : transactions) {
            Integer amount = transaction.getAmount().getMinorUnits();
        }
//        transferIntoSavingsGoal(amount);
        return;
    }

    /**
     * Tranfer round up amount into a savings goal.
     * @param amount transaction amount
     * @return none
     */
    public void transferIntoSavingsGoal(Integer amount)  {

        return;
    }

    //  throw an exception
//    public Integer calculate(final List<Transaction> transactions)  {
//        for (final Transaction transaction : transactions) {
//            if (transaction.getAmount().compareTo(BigDecimal.ZERO) > 0) {
//                result = result.add(this.roundingUpAmount(transaction));
//            }
//        }
//
//        return result;
//    }

}
