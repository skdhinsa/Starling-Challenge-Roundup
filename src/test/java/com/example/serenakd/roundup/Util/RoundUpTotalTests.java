package com.example.serenakd.roundup.Util;

import com.example.serenakd.roundup.util.RoundUpTotal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RoundUpTotalTests {
	@Autowired
	private RoundUpTotal roundUpTotal;

	@Test
	public void shouldRoundUpWeeklyTransactions() throws Exception {
		List<Integer> transactions = new ArrayList<Integer>();
		transactions.add(87);
		transactions.add(435);
		transactions.add(520);

		int expected = 158;
		int actual = roundUpTotal.calculate(transactions);
		assertEquals(expected, actual);
	}

	@Test
	public void shouldNotIncludeTransactionsDivisibleBy100InRoundUp() throws Exception {
		List<Integer> transactions = new ArrayList<Integer>();
		transactions.add(87);
		transactions.add(435);
		transactions.add(10000);

		int expected = 78;
		int actual = roundUpTotal.calculate(transactions);
		assertEquals(expected, actual);
	}
}
