package com.example.serenakd.roundup.service.Impl;

import com.example.serenakd.roundup.model.Account;
import com.example.serenakd.roundup.model.TransactionResponse;
import com.example.serenakd.roundup.service.AccountService;
import com.example.serenakd.roundup.service.TransactionsBetweenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.serenakd.roundup.util.RestClient.GET_TRANSACTIONS_BETWEEN_DATES_API;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class TransactionsBetweenServiceImpl implements TransactionsBetweenService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionsBetweenService.class);

//    @Value("${bearerToken}")
//    private String bearerToken;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccountService accountService;

    private HttpHeaders httpHeaders;

    /**
     * @return List of transactions between min and max timestamp
     */
    @Override
    public List<Integer> getTransactionsBetweenDates(){
        String bearerToken ="Bearer eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31Uy47bMAz8lcLn1cLxO7711h_oB1AUlQixJUOSd7so-u-VLTmO06C3zAwfQ4rO70w5l_UZTIoJGs2782AHpS8c9O0dzZi9ZW7mIUI0WJ1lW7G6qytWFTWxri0KRsg7TjwvilMdgunXlPWnpi7LwFTdW6bARyLo3UIAopm1_2EGQfanEqE25QXPy1oEC5SzSlDDuMSSVZJj053kqcNzqO3NjXTMaGQpGimB5bI9s4rnLeMcW9YS5mXL26JqqpARxvqOSM7FrJqf87wVyLggyaqyW36dBON10VW8Efx8Wvo4NBMtS4lOGQ7Gkegtgfi2cdfVPtMw0kvBf01PghKkvZKK7JEflPMHJgEhbDDek1D-DqLiPeB1pHvkjj-t8vQNZn81VrnwjExpoT6UmGGIwRwG0JisIVjB0GhvzRAbLUzSjJbKjuCV0cxIJmct3F1y9-4biK1xdt6M24g0gkqFBwpG9KWHaRq-7miNGkEL8NQLGiiU2GDS7I38MshkSZKl4N39T4o2ojYNgBQ24Oli1zkeE_8VUypZvMI23UgeghvoMcBVTXgdaoIvok2KIA0RwR7E1AiXNFMiQmert2eJ8Q-yt6Ad4O460IzPw63fXpd2ancQ8W4i4q3AciPh3kbl95qDwWDiocJKMLMcyTObsqyRathGijMeqDXKEpKa_AG4oxQX7uAjPKJjF7P7OHBpmgO31nlk4sLCGbwqsYsvau1iLIpXEvNAgqWFJZq8DwPOU4ITbB9R-Mdcj5kZKx7aH9mt75F9kc_Mp77zntYXQ_fxTE1CJmrmDm3Y5nIoW5dHbo16vKb1wZ7PK_vzF_jBF_cKBgAA.k0mDkKChHVU_2wPhO4RJp7G7hedEJqyhLPu6i1LLEJaVAM6lOP1DP9a9R0QT7qiP4d0GwBRIfynwxDXOs_vjbch2aB59EJkB8bO1fQyFfu8KfrwNZDEfrwj-2HnWOCm9QJNcNn7MExHN1hM_r_886Gudsfo2yb0_vMD9POPRWFNb0BKwHGAtuuxD8-Bu-9PGu9s6PbM3V5cfn4hvatmotav8XUJPOqbrtOp_Ir9EmnNgw_HtfmiJF4Wd1kB6eexsBitLhxomwc5b95GWzadHVgce8QfgdybxC5UuHwR9I-L735Aj5GTpuFBV2mDRJP1sAAXcGI73OJZOCPnKEjGXAT1xudpq3jxsM-dWWNFnRx0SOvG4vz6O2yZXln80igi9nYmVhphYojLSw9RBjJl2TB90ZEfqyVRUBkSaVBPCO54hhP9yxtmZwinGm_UsdINfwEkrNz1C4i5UvfSiYnGOjCr4dQOgNtTDQZkHCt45fKNFV5K1rDeQu8LYSfrlJLofOy_P9Qp2nN9wj39H5M78qJVIL5D_sdhCVFmFiP26Jt4tvc3K8_wUolZ_i-43bADNyg32K3nanYQMgnaif8KeAhXhXteAYdcEL54m72q3XH-h4xwoUzjxwcWSqjyKywcW5NoZ33P7RQD6TZH5cZy7xB-XMSaSbc900-jujxPaa0w";
        String accountUid = accountService.getAccountInfoAPI().getAccountUid();
        String categoryUid = accountService.getAccountInfoAPI().getDefaultCategory();
        String urlTemplate = String.format("https://api-sandbox.starlingbank.com/api/v2/feed/account/%s/category/%s/transactions-between?minTransactionTimestamp=2022-05-12T00:00:00.000Z&maxTransactionTimestamp=2022-05-19T23:59:00.000Z",
                accountUid, categoryUid);

        httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.set(AUTHORIZATION, bearerToken);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        restTemplate = new RestTemplate();

        ResponseEntity<TransactionResponse> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, TransactionResponse.class);
        logger.info("Retrieved ist of transactions in given week");
        return Objects.requireNonNull(response.getBody()).getFeedItems().stream().map(t -> t.getAmount().getMinorUnits()).toList();
    }
}
