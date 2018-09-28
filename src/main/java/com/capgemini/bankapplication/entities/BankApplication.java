package com.capgemini.bankapplication.entities;

public class BankApplication {
	
	@Override
	public String toString() {
		return "BankApplication [accountId=" + accountId + ", accountType=" + accountType + ", balance=" + balance
				+ "]";
	}

	private long accountId;
	private String accountType;
	private double balance;
	
	public BankApplication() {
		super();
	}

	public BankApplication(long accountId, String accountType, double balance) {
		super();
		this.accountId = accountId;
		this.accountType = accountType;
		this.balance = balance;
	}

	public long getAccountId() {
		return accountId;
	}

	public  void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	

}
