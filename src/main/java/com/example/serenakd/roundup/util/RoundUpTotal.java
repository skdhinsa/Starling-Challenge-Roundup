package com.example.serenakd.roundup.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RoundUpTotal {

    /**
     * Calculate the total rounded up.
     * @param transactions the list of transactions
     * @return none
     */
    public int calculate(List<Integer> transactions)  {
        int sweepingAmount = 0;
        for (Integer transaction : transactions) {
            if(transaction%100 != 0) {
                sweepingAmount += 100-(transaction%100);
            }
        }
        return sweepingAmount;
    }


}
