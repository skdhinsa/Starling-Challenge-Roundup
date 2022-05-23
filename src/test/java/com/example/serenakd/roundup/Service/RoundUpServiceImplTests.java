package com.example.serenakd.roundup.Service;

import com.example.serenakd.roundup.service.Impl.AccountServiceImpl;
import com.example.serenakd.roundup.service.Impl.RoundUpServiceImpl;
import com.example.serenakd.roundup.service.Impl.SavingsGoalServiceImpl;
import com.example.serenakd.roundup.service.Impl.TransactionsServiceImpl;
import com.example.serenakd.roundup.util.RoundUpTotal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoundUpServiceImplTests {
	@InjectMocks
	private RoundUpServiceImpl roundUpService;
	@Mock
	private AccountServiceImpl accountService;
	@Mock
	private SavingsGoalServiceImpl savingsGoalService;

	@Mock
	private TransactionsServiceImpl TransactionsBetweenService;

	@Mock
	private RoundUpTotal roundUpTotal;

	// TODO: 23/05/2022 ALL THE TESTS
	@Before
	public void setUp(){
		openMocks(this);
	}

	@Test
	public void shouldCallAddToSavingsGoal() throws Exception {
//		roundUpService.sweepAmountIntoSavingsGoal();
//		verify(savingsGoalService, Mockito.times(1)).addToSavingsGoal(UUID.randomUUID().toString(), 100);
	}

	@Test
	public void shouldReturnRoundedUpWeeklyTransactionsAmount() throws Exception {
		assertNotNull(this.accountService.getAccounts());
	}

	@Test
	public void shouldReturnSavingsGoalUid() throws Exception {
		roundUpService.getSavingsGoalUid();
		assertNotNull(this.accountService.getAccounts());
	}

}
