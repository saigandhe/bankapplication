package com.capgemini.bankapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.capgemini.bankapplication.controller.CustomerController;
import com.capgemini.bankapplication.entities.Customer;
import com.capgemini.bankapplication.exception.AccountNotFound;
import com.capgemini.bankapplication.exception.InsufficientAccountBalanceException;
import com.capgemini.bankapplication.exception.NegativeAmountException;
import com.capgemini.bankapplication.service.CustomerService;
import com.capgemini.bankapplication.service.impl.BankApplicationServiceImpl;
import com.capgemini.bankapplication.service.impl.CustomerServiceImpl;

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
	
	@Autowired
	CustomerServiceImpl customerServiceImpl;
	

	@Autowired
	CustomerController customercontroller;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;

	@Test
	public void testauthenticate() throws DataAccessException, AccountNotFound {
		Customer cust = new Customer();
		cust.setCustomerId(12);
		cust.setCustomerPassword("pass");
		BindingResult bindingResult = new BeanPropertyBindingResult(cust, "customer");
		String result = customercontroller.customerLogin(request, session, cust, bindingResult);
	}
	
	@Test
	public void test
	
	
	
}

