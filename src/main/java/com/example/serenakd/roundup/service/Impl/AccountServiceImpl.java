package com.example.serenakd.roundup.service.Impl;

import com.example.serenakd.roundup.model.Account;
import com.example.serenakd.roundup.model.AccountResponse;
import com.example.serenakd.roundup.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

//    @Value("${bearerToken}")
//    private String bearerToken;
// TODO: 21/05/2022 : add from application.properties
    @Autowired
    private RestTemplate restTemplate;

    private HttpHeaders httpHeaders;

    /**
     * @return Account
     */
    @Override
    public Account getAccountInfoAPI(){
        String bearerToken ="Bearer eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31U246bMBD9lYrn9SoQIIS3vvUH-gHj8TixYmxkm92uqv57DTYhpFFfIp1z5nJmPOR3obwv-gJGxQQN9t0HcFqZCwdze0c7FG-Fn3iMEC3WZ3mqWdM1Naurhlh3qipGyDtO_FBVZROD6ddY9GXbHKtDc6i6t0JBSERZdu1MAKKdTPhhtSD3U4lYmw4VPxwbES3QgdWCWsYlHlktObZdKcsOz7F2sDcyKaPt6FxX7ZEdK2xYDfGnE7xkByy7Ey95XbYyZsSxviOS9ymrFGesJJfsRMdY_dQAOx8JWEUtcEklVDXOA6MdaV5KcspQW0-idwTi28pdF_vMwEAvhfA1PglKkAlKKnJ7XisfdkwGQrhovCehwh0kJQTA60D3yA1_OhXoG0zhap3y8RmZMkJ9KDGBTsEcNBjM1hCcYGhNcFanRjOTNWukcgMEZQ2zksnJCH-X_L37ClJrnHywwzoiDaByYU3RiLn0MI76646WqAGMgEC9IE2xxAqz5m4U5kFGR5IcRe_-f1KykbRRA1LcQKCLW-Z4TPxXzKnk8ArrdAMFiG6gxwgXNeNlqBG-iFYpgTxEAlsQUwNc8kyZiJ2dWZ8lxT_IwYHxgJvrSDM-6Vu_vi5t1OYg4c1EwmuB-UbivQ0qbDW1xWjiocJCMDsfyTObs5yVSq8jpRl31BLlCEmNYQf8XkoL9_ARH9Gzi9187Lg8zY5b6jwyaWHxDF6V2MQXtTYxFcUriUmTYHlhmaYQ4oDTmOEI60cU_zGXY2bWiYf2e3btu2df5DP7ae58oOXF0H88U6OQmZq4Rxe3OR_K2uWRW6Ier2l5sOfzKv78BQyavbwKBgAA.J23K3aD3YA54ZS_yj9zbZ-fknczUoGsGVnJQqcgOEmULQR4KkHP9d-kNlauAgIefRG5naKpOy3luGJ8_FCBA1dPxRrKEgZJb3bG1E7xSs-JpLlNHsB_RwA3AA7OLI2pMuWif5V02bmRrgV5eViv3M02SnLVhK1nkZ7axRm5lXWOFg9w-WX6qYCqnoSf8sj4dmOMc0LnNgqhkskukJz7li7BRo_O4plu4b2JCb_2sFbkp8kcRpSeExd7m2Hhg3r8PMIT1OYFie-3e0uX8TEixL-LpIVNuZ-VEWyufEqX1wwiyzXgARfjp8_MGnNrh5S8_BdyXHyywHF6x5v975t2g_KJkIEBu_rdb8BwC99JgB4YmKNLl-IzixZRIucSsejmL-hqJBmVFDS_U3PWBTUpO0YtpB63-ruv23-mJQ4W_gFGaX-upv4TQRqg1aOMOeLdUB52VmJyw6FS81FgslVhwCpA3eK727F0pBum7AFemmpRcf91deMveSRx8gDzroROFePlG3nBzzr4o01jVfII89VkHLDwNC74m5jcsaLc4nutXrXzX0yWsiMEEPUHzkBJmgu_uLMLS31uA8QW9UlHfwxUeIAQzLVPj3F454p6L9mZ-w9LFn1MJrXmTWr7aJzJc2-Qd1V-Gyb0znVwb9nWxfPSDuP1r5Bu0r2U5KufVCIU";
        httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.set(AUTHORIZATION, bearerToken);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        restTemplate = new RestTemplate();
        ResponseEntity<AccountResponse> response = restTemplate.exchange(GET_ACCOUNT_INFO_API, HttpMethod.GET, entity, AccountResponse.class);
        logger.info("Retrieving account information: ", Objects.requireNonNull(response.getBody()).getAccounts().get(0));
        return Objects.requireNonNull(response.getBody()).getAccounts().get(0);
    }
}