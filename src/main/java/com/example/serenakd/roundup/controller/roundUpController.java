package com.example.serenakd.roundup.controller;

import com.example.serenakd.roundup.model.Account;
import com.example.serenakd.roundup.service.Impl.AccountServiceImpl;
import com.example.serenakd.roundup.service.Impl.RoundUpServiceImpl;
import com.example.serenakd.roundup.service.Impl.TransactionsBetweenServiceImpl;
import com.example.serenakd.roundup.service.TransactionsBetweenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class roundUpController {

    @Autowired
    RoundUpServiceImpl roundUpService;

    /**
     * @return Create a savings item in savings goal list
     */
    @GetMapping("/roundup")
    @ResponseBody
    public ResponseEntity<Integer> postRoundUp() throws Exception {
        int response = roundUpService.putSweepAmountIntoSavingsGoal();
        return new ResponseEntity<Integer>(response, HttpStatus.OK);
    }

}
