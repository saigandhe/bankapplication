package com.capgemini.bankapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.bankapplication.exception.InsufficientAccountBalanceException;
import com.capgemini.bankapplication.exception.NegativeAmountException;
import com.capgemini.bankapplication.service.impl.BankApplicationServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankapplicationApplicationTests {

	@Autowired
	BankApplicationServiceImpl bankApplicationServiceImpl;
	
	@Test
	public void contextLoads() throws InsufficientAccountBalanceException, NegativeAmountException {
		boolean result = bankApplicationServiceImpl.fundTransfer(123456, 12345, 1);
		System.out.println(result);
		assertTrue(result);
	}
	
}

