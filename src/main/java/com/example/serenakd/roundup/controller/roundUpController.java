package com.example.serenakd.roundup.controller;

import com.example.serenakd.roundup.service.TransactionsBetweenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class roundUpController {

    @Autowired
    TransactionsBetweenService transactionsBetweenService;

    /**
     * @return Create a savings item in savings goal list
     */
    @GetMapping("/roundup")
    @ResponseBody
    public ResponseEntity<String> postRoundUp() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String response = "change to json response";
        return new ResponseEntity<String>(response, httpHeaders, HttpStatus.OK);
    }

}
