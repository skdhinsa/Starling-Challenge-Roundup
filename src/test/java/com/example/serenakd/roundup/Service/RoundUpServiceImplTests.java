package com.example.serenakd.roundup.Service;

import com.example.serenakd.roundup.model.Account;
import com.example.serenakd.roundup.service.Impl.AccountServiceImpl;
import com.example.serenakd.roundup.service.Impl.RoundUpServiceImpl;
import com.example.serenakd.roundup.service.Impl.SavingsGoalServiceImpl;
import com.example.serenakd.roundup.service.Impl.TransactionsBetweenServiceImpl;
import com.example.serenakd.roundup.util.RoundUpTotal;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoundUpServiceImplTests {

	@Mock
	private AccountServiceImpl accountService;
	@Mock
	private SavingsGoalServiceImpl savingsGoalService;
	@Mock
	private TransactionsBetweenServiceImpl transactionsBetweenService;
	@Mock
	private RoundUpTotal roundUpTotal;
	@Mock
	private static RestTemplate restTemplate;
	@InjectMocks
	private RoundUpServiceImpl roundUpService;

	@Before
	public void setUp() throws IOException {
		openMocks(this);
		roundUpService = new RoundUpServiceImpl(transactionsBetweenService, savingsGoalService, roundUpTotal);

		ObjectMapper mapper = new ObjectMapper();
		String file = "src/test/java/resources/JsonResponses/accountResponse.json";
		AccountServiceImpl.AccountResponse responseBody;

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

		Account acctStub = new Account("d22353cd-0a99-4bc9-818d-0cebbd721f8d", "PRIMARY",
				"ae58a8e1-f529-4bf8-b3ff-c600b044baec","GBP", "2022-05-10T14:05:32.462Z",
				"Personal");
		when(accountService.getAccounts()).thenReturn(acctStub);
	}

	@Test
	public void shouldReturnRoundedUpWeeklyTransactionsAmount() throws Exception {
		roundUpService.roundUpTransactionsForWeek("2022-04-01T14:05:32.462Z", "2022-04-01T14:05:32.462Z");
		verify(roundUpTotal, times(1)).calculate(anyList());
	}

	@Test
	public void shouldReturnExistingSavingsGoalUid() {
		when(savingsGoalService.isGoalAlreadyPresent()).thenReturn(Boolean.TRUE);
		roundUpService.getSavingsGoalUid();
		verify(savingsGoalService, times(1)).getRoundUpSavingsGoalUid();

	}

	@Test
	public void shouldReturnNewSavingsGoalUid() {
		when(savingsGoalService.isGoalAlreadyPresent()).thenReturn(Boolean.FALSE);
		roundUpService.getSavingsGoalUid();
		verify(savingsGoalService, times(1)).createNewSavingsGoalUid();
	}

}
