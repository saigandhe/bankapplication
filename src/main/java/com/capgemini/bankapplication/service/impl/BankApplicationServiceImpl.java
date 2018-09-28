package com.capgemini.bankapplication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.bankapplication.exception.InsufficientAccountBalanceException;
import com.capgemini.bankapplication.exception.NegativeAmountException;
import com.capgemini.bankapplication.repository.impl.BankApplicationRepositoryImpl;
import com.capgemini.bankapplication.service.BankApplicationService;

@Service
public class BankApplicationServiceImpl implements BankApplicationService {

	@Autowired
	BankApplicationRepositoryImpl bankapprepositoryimpl;

	@Autowired
	BankApplicationServiceImpl serviceobjects;

	@Override
	public double getBalance(long accountId)  {

		return bankapprepositoryimpl.getBalance(accountId);
	}

	@Override
	public double withdraw(long accountId, double amount) {

		double accountBalance = bankapprepositoryimpl.getBalance(accountId);
		bankapprepositoryimpl.updateBalance(accountId, accountBalance - amount);
		return accountBalance - amount;
	}

	@Override
	public double deposit(long accountId, double amount) {

		double accountBalance = bankapprepositoryimpl.getBalance(accountId);
		bankapprepositoryimpl.updateBalance(accountId, accountBalance + amount);
		return accountBalance + amount;

	}

	@Override
	public boolean fundTransfer(long fromAcc, long toAcc, double amount)
			throws InsufficientAccountBalanceException, NegativeAmountException {

		double accountBalanceFrom = bankapprepositoryimpl.getBalance(fromAcc);
	
		if (accountBalanceFrom < amount)
			throw new InsufficientAccountBalanceException("There isn't sufficient balance in your account!");
		else if (amount < 0)
			throw new NegativeAmountException("The amount cannot be negative!");
		else {

			serviceobjects.deposit(toAcc, amount);
			serviceobjects.withdraw(fromAcc, amount);
			
			return true;
		}

	}
}
