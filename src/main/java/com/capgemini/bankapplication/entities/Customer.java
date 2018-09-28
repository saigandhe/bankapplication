package com.capgemini.bankapplication.entities;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class Customer {
	
	@NotNull(message = "Id must be entered.")
	private long customerId;
	private String customerName;
	@NotEmpty(message = "Password should not be empty.")
    /*@Size(min = 2, max = 15, message = "Your password must between 2 and 15 characters")*/
	private String customerPassword;
	@Email
	//@Size(min=6,max=20, message = "Your emailid should not be empty")
	private String customerEmailId;
	private String customerAddress;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate customerDateOfBirth;
	private BankApplication account;
	
	public Customer() {
		super();
		
	}
	public Customer(long customerId, String customerName, String customerPassword, String customerEmailId,
			String customerAddress, LocalDate customerDateOfBirth,BankApplication app) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerPassword = customerPassword;
		this.customerEmailId = customerEmailId;
		this.customerAddress = customerAddress;
		this.customerDateOfBirth = customerDateOfBirth;
		this.account = app;
	}
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", customerPassword="
				+ customerPassword + ", customerEmailId=" + customerEmailId + ", customerAddress=" + customerAddress
				+ ", customerDateOfBirth=" + customerDateOfBirth + ", account=" + account + "]";
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPassword() {
		return customerPassword;
	}
	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}
	public String getCustomerEmailId() {
		return customerEmailId;
	}
	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public LocalDate getCustomerDateOfBirth() {
		return customerDateOfBirth;
	}
	public void setCustomerDateOfBirth(LocalDate customerDateOfBirth) {
		this.customerDateOfBirth = customerDateOfBirth;
	}
	public BankApplication getAccount() {
		return account;
	}
	public void setAccount(BankApplication account) {
		this.account = account;
	}
	
	
}