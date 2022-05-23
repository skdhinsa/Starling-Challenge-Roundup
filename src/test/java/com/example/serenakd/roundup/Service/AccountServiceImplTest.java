package com.example.serenakd.roundup.Service;

import com.example.serenakd.roundup.service.Impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AccountServiceImplTest {

    @Autowired
    private AccountServiceImpl accountService;


    @Test
    public void shouldReturnFirstAccountInList(){
        assertNotNull(this.accountService.getAccounts());
    }

}