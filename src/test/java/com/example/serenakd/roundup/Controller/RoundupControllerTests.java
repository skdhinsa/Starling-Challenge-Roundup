package com.example.serenakd.roundup.Controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoundupControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnStatus200() throws Exception {
		this.mockMvc.perform(get("/api/v1/roundup")
						.contentType("application/json"))
				.andExpect(status().isOk());
	}

}
