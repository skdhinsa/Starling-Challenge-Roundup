package com.example.serenakd.roundup.Controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoundupControllerTests {
	@Autowired
	private MockMvc mockMvc;

	final String ACCOUNT_UID = "d22353cd-0a99-4bc9-818d-0cebbd721f8d";
	final String MIN_TRANSACTION_TIME = "2022-05-12T00:00:00.000Z";
	final String MAX_TRANSACTION_TIME = "2022-05-19T23:59:00.000Z";
	final String ROUND_UP_API_URL = String.format("/api/v1/feed/account/%s/savings-goals/round-up/transactions-between?minTransactionTimestamp=%s&maxTransactionTimestamp=%s"
			,ACCOUNT_UID, MIN_TRANSACTION_TIME, MAX_TRANSACTION_TIME);
	@Test
	public void shouldReturnStatus200() throws Exception {
		this.mockMvc.perform(put(ROUND_UP_API_URL)
						.contentType("application/json"))
				.andExpect(status().isOk());
	}

}
