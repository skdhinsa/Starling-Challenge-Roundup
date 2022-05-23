package com.example.serenakd.roundup.controller;

import com.example.serenakd.roundup.service.Impl.RoundUpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1")
public class roundUpController {

    private final RoundUpServiceImpl roundUpService;

    @Autowired
    public roundUpController(RoundUpServiceImpl roundUpService){
        this.roundUpService = roundUpService;
    }

    /**
     * @return Create a savings item in savings goal list
     */
    @PutMapping("feed/account/{accountUid}/savings-goals/round-up/transactions-between")
    @ResponseBody
    public ResponseEntity<?> roundUp(@PathVariable("accountUid") String accountUid,
                                        @RequestParam("minTransactionTimestamp") String minTransactionTimestamp,
                                        @RequestParam("maxTransactionTimestamp") String maxTransactionTimestamp) throws Exception {
        roundUpService.sweepRoundedAmountIntoSavingsGoal(accountUid, minTransactionTimestamp, maxTransactionTimestamp);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
