package com.capgemini.bankapplication.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.capgemini.bankapplication.entities.Customer;
import com.capgemini.bankapplication.exception.AccountNotFound;
import com.capgemini.bankapplication.exception.InsufficientAccountBalanceException;
import com.capgemini.bankapplication.exception.NegativeAmountException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(value = AccountNotFound.class)
	public String handleError(HttpServletRequest request, AccountNotFound exception, Model model) {
		System.out.println(exception);

		request.setAttribute("name", "true");
		request.setAttribute("accountnotfound", "true");
		System.out.println(exception.getCause());
		model.addAttribute("customer", new Customer());
		return "index";
	}

	@ExceptionHandler(value = InsufficientAccountBalanceException.class)
	public String handleError(HttpServletRequest request, InsufficientAccountBalanceException exception) {
		System.out.println(exception);
		request.setAttribute("insufficientbalance", "true");
		request.setAttribute("error", exception);
		System.out.println(exception.getCause());
		return "fundTransfer";

	}

	@ExceptionHandler(value = NegativeAmountException.class)
	public String handleError(HttpServletRequest request, NegativeAmountException exception) {
		System.out.println(exception);
		request.setAttribute("negativeamount", "true");
		request.setAttribute("error", false);
		System.out.println(exception.getCause());
		return "fundTransfer";
	}

}