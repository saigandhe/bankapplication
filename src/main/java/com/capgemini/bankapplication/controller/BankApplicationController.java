package com.capgemini.bankapplication.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.capgemini.bankapplication.entities.Customer;
import com.capgemini.bankapplication.exception.InsufficientAccountBalanceException;
import com.capgemini.bankapplication.exception.NegativeAmountException;
import com.capgemini.bankapplication.service.impl.BankApplicationServiceImpl;
import com.capgemini.bankapplication.service.impl.CustomerServiceImpl;

@Controller
public class BankApplicationController {
	
	@Autowired
	private CustomerServiceImpl customerServiceImpl;
	
	@Autowired
	private BankApplicationServiceImpl bankApplicationServiceImpl;
	
	@Autowired
	 HttpServletRequest request;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String getLoginPage(Model model) {
		model.addAttribute("customer", new Customer());
		return "index";
	
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String customerLogin(@Valid @ModelAttribute Customer customer,BindingResult result) {
		if(result.hasErrors()) {
			return "index";
		}
		request = (HttpServletRequest )request;
		if(null == request.getCookies()) {
			return "enableCookie";
		}
		else {
			try {
				customer = customerServiceImpl.authenticate(customer);
		
			} catch( EmptyResultDataAccessException|NumberFormatException e) {
				request.setAttribute("name", "true");
				customer=null;
			}finally {
				if(customer != null) {
					request.getSession(false);
					
					session.setAttribute("customer", customer);
					return "redirect:/home";}
					else
						return "index";
				}
			}
		}
		


	@RequestMapping(value="/home",method = RequestMethod.GET)
	public String homepage() {
	
		request.getSession(false);
		Customer cust = (Customer)session.getAttribute("customer");
		Customer customer = customerServiceImpl.updateSession(cust.getCustomerId());
		
		request.getSession().setAttribute("customer", customer);
		System.out.println(customer);
		return "home";
	}
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String editProfile(Model model) {
		System.out.println("amulbaby");
		model.addAttribute("customer", session.getAttribute("customer"));
		request.getSession(false);
		if(null==session.getAttribute("customer")) {
			return "error";
		}
		else {
			return "edit";
		}
	}
	
	@RequestMapping(value="/fundTransferMethod",method=RequestMethod.POST)
	public String fundTransferMethod()  {
		try {
			long from = Long.parseLong(request.getParameter("fromAcc"));
			long to = Long.parseLong(request.getParameter("toAcc"));
			double amount  = Double.parseDouble(request.getParameter("amount"));
			if(bankApplicationServiceImpl.fundTransfer(from, to, amount)) {
				System.out.println("fund");
				request.setAttribute("success", "true");
				
				Customer cust = (Customer) session.getAttribute("customer");
				Customer customer = customerServiceImpl.updateSession(cust.getCustomerId());
				request.getSession().setAttribute("customer", customer);
				//System.out.println();
			}
		}
		catch(EmptyResultDataAccessException | InsufficientAccountBalanceException | NegativeAmountException e) {
			if(e instanceof NegativeAmountException) {
				request.setAttribute("negativeamount", "true");
			}
			else if(e instanceof InsufficientAccountBalanceException) {
				request.setAttribute("insufficientbalance", "true");
			}
			else {
				request.setAttribute("accountnotfound", "true");
			}
		}
		return "fundTransfer";
		
	}
	
	@RequestMapping(value="/fundTransfer", method = RequestMethod.GET)
	public String fundTransfer() {
		request.getSession(false);
	if(null == session.getAttribute("customer")) {
		return "error";
	}else 
	{
		return "fundTransfer";
	}
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(Model model) {
		request.getSession(false);
		session.invalidate();
		model.addAttribute("customer", new Customer());
		return "index";
	}
	
	@RequestMapping(value="/updatePassword", method = RequestMethod.POST)
	public String updatePassword() {
		request.getSession(false);
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		
		Customer customer = (Customer) (session.getAttribute("customer"));
		if (oldPassword.equals(customer.getCustomerPassword())) {
			if (customerServiceImpl.updatePassword(customer, oldPassword, newPassword)) {
				return "redirect:/home";
			} else {
				request.setAttribute("passwordnotchanged", "true");
				return "changePassword";
			}
		} else {
			request.setAttribute("oldpassword", "false");
			return "changePassword";
		}
	}
	
	@RequestMapping(value="/updatePasswordMethod", method = RequestMethod.GET)
	public String updatePasswordMethod() {
		HttpSession session = request.getSession(false);
		if (null == session.getAttribute("customer")) {

			return "error";
		} else {
			return "changePassword";
		}
	}
	
	@RequestMapping(value="/updateProfile", method = RequestMethod.POST)
	public String updateProfile(@ModelAttribute Customer customer) {
		customer = customerServiceImpl.updateProfile(customer);
		if(customer!= null) {
			request.getSession().setAttribute("customer", customer);
			return "redirect:/home";
		}else
		{
			request.setAttribute("profileupdate", "false");
			return "edit";
		}
		
	}
	
	
}

