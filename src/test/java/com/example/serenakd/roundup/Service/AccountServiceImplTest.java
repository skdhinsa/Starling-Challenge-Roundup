package com.example.serenakd.roundup.Service;

import com.example.serenakd.roundup.model.Account;
import com.example.serenakd.roundup.service.Impl.AccountServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private AccountServiceImpl accountService;

    private final ObjectMapper mapper = new ObjectMapper();;
    private final String file = "src/test/java/resources/JsonResponses/accountResponse.json";
    private AccountServiceImpl.AccountResponse responseBody;

    @Before
    public void setUp() throws IOException {
        openMocks(this);
        accountService = new AccountServiceImpl(restTemplate);

        String jsonResponse = new String(Files.readAllBytes(Paths.get(file)));
        responseBody = mapper.readValue(jsonResponse, new TypeReference<>() {});
        ResponseEntity<AccountServiceImpl.AccountResponse> response =
                new ResponseEntity<>(responseBody, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(HttpMethod.class),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.<Class<AccountServiceImpl.AccountResponse>>any()))
                .thenReturn(response);

    }
    @Test
    public void shouldReturnNonNullResponse() {
        Account res = accountService.getAccounts();
        assertNotNull(res);
    }

    @Test
    public void shouldReturnPrimaryAccountFromList() {
        String expected = "PRIMARY";
        assertEquals(expected, this.accountService.getAccounts().accountType());
    }

    @Test
    public void shouldReturnFirstAccountInList() {
        assertEquals(responseBody.accounts().get(0), accountService.getAccounts());
    }

}