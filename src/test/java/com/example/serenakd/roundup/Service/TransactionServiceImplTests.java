package com.example.serenakd.roundup.Service;

import com.example.serenakd.roundup.service.AccountService;
import com.example.serenakd.roundup.service.Impl.TransactionsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TransactionServiceImplTests {

	@InjectMocks
	private TransactionsServiceImpl transactionsBetweenService;
	@Mock
	private AccountService accountService;

	@Before
	public void setUp(){
		openMocks(this);
	}

	@Test
	public void shouldReturnListofTransactionsInGivenWeek() {
		openMocks(this);
		when(this.accountService.getAccounts().accountUid()).thenReturn("d22353cd-0a99-4bc9-818d-0cebbd721f8d");
		when(this.accountService.getAccounts().defaultCategory()).thenReturn("ae58a8e1-f529-4bf8-b3ff-c600b044baec");
//		assertNotNull(this.transactionsBetweenService.getTransactionsBetweenDates());
	}

}
