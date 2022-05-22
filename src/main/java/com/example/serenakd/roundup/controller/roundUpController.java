package com.example.serenakd.roundup.controller;

import com.example.serenakd.roundup.service.Impl.RoundUpServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
public class roundUpController {

    @Autowired
    RoundUpServiceImpl roundUpService;

    /**
     * @return Create a savings item in savings goal list
     */
    @GetMapping("/roundup")
    @ResponseBody
    public ResponseEntity<?> postRoundUp() throws Exception {
        roundUpService.putSweepAmountIntoSavingsGoal();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
