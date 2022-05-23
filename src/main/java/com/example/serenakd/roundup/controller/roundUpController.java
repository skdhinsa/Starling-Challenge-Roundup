package com.example.serenakd.roundup.controller;

import com.example.serenakd.roundup.service.Impl.RoundUpServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


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
    @GetMapping("feed/account/{accountUid}/savings-goals/round-up")
    @ResponseBody
    public ResponseEntity<?> getRoundUp(@PathVariable("accountUid") String accountUid) throws Exception {
        roundUpService.sweepAmountIntoSavingsGoal(accountUid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
