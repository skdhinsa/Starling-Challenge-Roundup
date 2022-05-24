package com.example.serenakd.roundup.Service;

import com.example.serenakd.roundup.model.Account;
import com.example.serenakd.roundup.model.SavingsGoals;
import com.example.serenakd.roundup.service.Impl.AccountServiceImpl;
import com.example.serenakd.roundup.service.Impl.RoundUpServiceImpl;
import com.example.serenakd.roundup.service.Impl.SavingsGoalServiceImpl;
import com.example.serenakd.roundup.service.Impl.TransactionsBetweenServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SavingsGoalServiceImplTests {

	@Mock
	private AccountServiceImpl accountService;
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private Environment environment;
	@InjectMocks
	private SavingsGoalServiceImpl savingsGoalService;
	private SavingsGoalServiceImpl.SavingsGoalsResponse responseBody;
	private ObjectMapper mapper;
	private String file;
	private String jsonResponse;

	@Before
	public void setUp() throws IOException {
		openMocks(this);

		Account acctStub = new Account("d22353cd-0a99-4bc9-818d-0cebbd721f8d", "PRIMARY",
				"ae58a8e1-f529-4bf8-b3ff-c600b044baec","GBP", "2022-05-10T14:05:32.462Z",
				"Personal");
		when(accountService.getAccounts()).thenReturn(acctStub);

		savingsGoalService = new SavingsGoalServiceImpl(restTemplate, accountService, environment);
		mapper = new ObjectMapper();
	}

	@Test
	public void shouldReturnNonNullResponseForListOfSavingsGoals() throws IOException {
		file = "src/test/java/resources/JsonResponses/savingsGoalListResponse.json";
		jsonResponse = new String(Files.readAllBytes(Paths.get(file)));
		responseBody = mapper.readValue(jsonResponse, new TypeReference<>() {});
		ResponseEntity<SavingsGoalServiceImpl.SavingsGoalsResponse> response =
				new ResponseEntity<>(responseBody, HttpStatus.OK);
		Mockito.when(restTemplate.exchange(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.any(HttpMethod.class),
						ArgumentMatchers.any(),
						ArgumentMatchers.<Class<SavingsGoalServiceImpl.SavingsGoalsResponse>>any()))
				.thenReturn(response);

		assertNotNull(savingsGoalService.getAllSavingsGoals());
	}

	@Test
	public void shouldReturnTrueIfGoalAlreadyExists() throws IOException {
		file = "src/test/java/resources/JsonResponses/savingsGoalListResponse.json";
		jsonResponse = new String(Files.readAllBytes(Paths.get(file)));
		responseBody = mapper.readValue(jsonResponse, new TypeReference<>() {});
		ResponseEntity<SavingsGoalServiceImpl.SavingsGoalsResponse> response =
				new ResponseEntity<>(responseBody, HttpStatus.OK);
		Mockito.when(restTemplate.exchange(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.any(HttpMethod.class),
						ArgumentMatchers.any(),
						ArgumentMatchers.<Class<SavingsGoalServiceImpl.SavingsGoalsResponse>>any()))
				.thenReturn(response);
		savingsGoalService.isGoalAlreadyPresent();
		assertTrue(true);
	}

	@Test
	public void shouldReturnFalseIfGoalDoesNotExists() throws IOException {
		file = "src/test/java/resources/JsonResponses/savingsGoalListResponse_GoalNotExist.json";
		jsonResponse = new String(Files.readAllBytes(Paths.get(file)));
		responseBody = mapper.readValue(jsonResponse, new TypeReference<>() {});
		ResponseEntity<SavingsGoalServiceImpl.SavingsGoalsResponse> response =
				new ResponseEntity<>(responseBody, HttpStatus.OK);
		Mockito.when(restTemplate.exchange(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.any(HttpMethod.class),
						ArgumentMatchers.any(),
						ArgumentMatchers.<Class<SavingsGoalServiceImpl.SavingsGoalsResponse>>any()))
				.thenReturn(response);
		savingsGoalService.isGoalAlreadyPresent();
		assertFalse(false);
	}

	@Test
	public void shouldReturnRoundUpSavingsGoalUid() throws IOException {
		file = "src/test/java/resources/JsonResponses/savingsGoalListResponse.json";
		jsonResponse = new String(Files.readAllBytes(Paths.get(file)));
		responseBody = mapper.readValue(jsonResponse, new TypeReference<>() {});
		ResponseEntity<SavingsGoalServiceImpl.SavingsGoalsResponse> response =
				new ResponseEntity<>(responseBody, HttpStatus.OK);
		Mockito.when(restTemplate.exchange(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.any(HttpMethod.class),
						ArgumentMatchers.any(),
						ArgumentMatchers.<Class<SavingsGoalServiceImpl.SavingsGoalsResponse>>any()))
				.thenReturn(response);
		assertEquals("6ffa6953-f580-47f4-9f4c-052570200e43", savingsGoalService.getRoundUpSavingsGoalUid());
	}

}
