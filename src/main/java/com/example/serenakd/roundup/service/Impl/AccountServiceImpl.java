package com.example.serenakd.roundup.service.Impl;

import com.example.serenakd.roundup.model.Account;
import com.example.serenakd.roundup.model.AccountResponse;
import com.example.serenakd.roundup.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;

import static com.example.serenakd.roundup.util.RestClient.GET_ACCOUNT_INFO_API;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Value("${bearerToken}")
    private String bearerToken;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @return Account
     */
    @Override
    public Account callGetAccountInfoAPI(){
        HttpHeaders httpsHeaders = new HttpHeaders();
        httpsHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpsHeaders.set(AUTHORIZATION, bearerToken);
        HttpEntity<String> entity = new HttpEntity<>(httpsHeaders);

        restTemplate = new RestTemplate();
        ResponseEntity<AccountResponse> response = restTemplate.exchange(GET_ACCOUNT_INFO_API, HttpMethod.GET, entity, AccountResponse.class);

        return Objects.requireNonNull(response.getBody()).getAccounts().get(0);
    }
}
