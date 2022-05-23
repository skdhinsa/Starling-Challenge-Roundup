package com.example.serenakd.roundup.Service;

import com.example.serenakd.roundup.service.Impl.AccountServiceImpl;
import com.example.serenakd.roundup.service.Impl.RoundUpServiceImpl;
import com.example.serenakd.roundup.service.Impl.SavingsGoalServiceImpl;
import com.example.serenakd.roundup.service.Impl.TransactionsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class SavingsGoalServiceImplTests {
	@Autowired
	private MockMvc mockMvc;
	@InjectMocks
	private SavingsGoalServiceImpl savingsGoalService;
	@Mock
	private AccountServiceImpl accountService;
	@Mock
	private RoundUpServiceImpl roundUpService;
	@Mock
	private TransactionsServiceImpl TransactionsBetweenService;

	@Before
	public void setUp(){
		openMocks(this);
	}

	@Test
	public void shouldReturnNewSavingsGoalUid() {

	}

	@Test
	public void shouldAddToSavingsGoal() {
//		non-null response
	}

	@Test
	public void shouldReturnListOfSavingsGoals() {

	}

	@Test
	public void shouldReturnIfGoalAlreadyPresent() {

	}

	@Test
	public void shouldReturnRoundUpSavingsGoalUid() {

	}

}
