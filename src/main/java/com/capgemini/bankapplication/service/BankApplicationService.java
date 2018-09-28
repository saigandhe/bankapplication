package com.capgemini.bankapplication.service;

import com.capgemini.bankapplication.exception.InsufficientAccountBalanceException;
import com.capgemini.bankapplication.exception.NegativeAmountException;

public interface BankApplicationService {
	public double getBalance(long accountId) ;
	public double withdraw(long accountId, double amount);
	public double deposit(long accountId, double amount) ;
	public boolean fundTransfer(long fromAcc, long toAcc, double amount) throws InsufficientAccountBalanceException, NegativeAmountException;

}
