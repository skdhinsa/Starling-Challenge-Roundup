package com.example.serenakd.roundup.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoundUpTotal {

    private static final Logger logger = LoggerFactory.getLogger(RoundUpTotal.class);
    private Integer sweepingAmount;

    /**
     * Calculate the total rounded up.
     * @param transactions the list of transactions
     * @return none
     */
    public Integer calculate(List<Integer> transactions)  {
        sweepingAmount = 0;
        for (Integer transaction : transactions) {
            if(transaction%100 != 0) {
                sweepingAmount += 100-(transaction%100);
            }
        }
        return sweepingAmount;
    }


}
