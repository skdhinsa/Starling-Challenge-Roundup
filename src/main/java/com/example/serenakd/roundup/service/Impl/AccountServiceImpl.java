package com.example.serenakd.roundup.service.Impl;

import com.example.serenakd.roundup.model.Account;
import com.example.serenakd.roundup.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static com.example.serenakd.roundup.util.RestAPIs.GET_ACCOUNT_INFO_API;
import static com.example.serenakd.roundup.util.Utilities.createHttpHeaders;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final Environment environment;

    public String getBearerToken() {
        return environment.getProperty("bearerToken");
    }

    @Autowired
    public AccountServiceImpl(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    /**
     * @return Account - the first account in list
     */
    @Override
    public Account getAccounts(){
        ResponseEntity<AccountResponse> response =
                restTemplate.exchange(GET_ACCOUNT_INFO_API, HttpMethod.GET, createHttpHeaders(getBearerToken(), httpHeaders),
                        AccountResponse.class);
        log.info("Retrieving account information");
        return Objects.requireNonNull(response.getBody()).accounts().stream()
                .filter(account -> account.accountType().equals("PRIMARY"))
                .toList().get(0);
    }


    public record AccountResponse(List<Account> accounts) {
    }
}
