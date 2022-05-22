package com.example.serenakd.roundup.controller;

import com.example.serenakd.roundup.service.Impl.RoundUpServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
    @GetMapping("/roundup")
    @ResponseBody
    public ResponseEntity<?> getRoundUp() throws Exception {
        roundUpService.sweepAmountIntoSavingsGoal();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
