package com.example.serenakd.roundup.Service;

import com.example.serenakd.roundup.model.Account;
import com.example.serenakd.roundup.service.Impl.AccountServiceImpl;
import com.example.serenakd.roundup.service.Impl.TransactionsBetweenServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
public class TransactionsBetweenServiceImplTests {

	@Mock
	private RestTemplate restTemplate;
	@Mock
	private AccountServiceImpl accountService;
	@InjectMocks
	private TransactionsBetweenServiceImpl transactionsBetweenServiceImpl;

	private final ObjectMapper mapper = new ObjectMapper();
	private final String file = "src/test/java/resources/JsonResponses/transactionFeedResponse.json";
	private TransactionsBetweenServiceImpl.TransactionResponse responseBody;

	@Before
	public void setUp() throws IOException {
		openMocks(this);
		transactionsBetweenServiceImpl = new TransactionsBetweenServiceImpl(restTemplate, accountService);
		String jsonResponse = new String(Files.readAllBytes(Paths.get(file)));
		responseBody = mapper.readValue(jsonResponse, new TypeReference<>() {});
		ResponseEntity<TransactionsBetweenServiceImpl.TransactionResponse> response =
				new ResponseEntity<>(responseBody, HttpStatus.OK);

		Mockito.when(restTemplate.exchange(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.any(HttpMethod.class),
						ArgumentMatchers.any(),
						ArgumentMatchers.<Class<TransactionsBetweenServiceImpl.TransactionResponse>>any()))
				.thenReturn(response);

		Account acctStub = new Account("d22353cd-0a99-4bc9-818d-0cebbd721f8d", "PRIMARY",
				"ae58a8e1-f529-4bf8-b3ff-c600b044baec","GBP", "2022-05-10T14:05:32.462Z",
				"Personal");
		when(accountService.getAccounts()).thenReturn(acctStub);
	}

	@Test
	public void shouldReturnNonNullResponse() {
		List<Integer> expected = transactionsBetweenServiceImpl
				.getTransactionsBetweenDates("2022-05-12T00:00:00.000Z","2022-05-19T23:59:00.000Z");
		assertNotNull(expected);
	}

}
