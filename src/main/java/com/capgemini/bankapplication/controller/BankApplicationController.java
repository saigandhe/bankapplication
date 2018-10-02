package com.capgemini.bankapplication.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.bankapplication.entities.Customer;
import com.capgemini.bankapplication.exception.InsufficientAccountBalanceException;
import com.capgemini.bankapplication.exception.NegativeAmountException;
import com.capgemini.bankapplication.service.BankApplicationService;
import com.capgemini.bankapplication.service.CustomerService;

@Controller
public class BankApplicationController {
	
	@Autowired
	private BankApplicationService bankApplicationService;
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/fundTransfer", method = RequestMethod.GET)
	public String fundTransfer(HttpServletRequest request, HttpSession session) {
		request.getSession(false);
		if (null == session.getAttribute("customer")) {

			return "error";
		} else {
			return "fundTransfer";
		}
	}
	
	@RequestMapping(value = "/fundTransferMethod", method = RequestMethod.POST)
	public String fundTransferMethod(HttpServletRequest request, HttpSession session,@RequestParam long fromAcc, @RequestParam long toAcc, @RequestParam double amount) throws InsufficientAccountBalanceException, NegativeAmountException {
		
			if (bankApplicationService.fundTransfer(fromAcc, toAcc, amount)) {
				request.setAttribute("success", "true");
				request.getSession();
				Customer cust = (Customer) session.getAttribute("customer");
				Customer customer = customerService.updateSession(cust.getCustomerId());
				request.getSession().setAttribute("customer", customer);
		}
		return "fundTransfer";
	}
}


