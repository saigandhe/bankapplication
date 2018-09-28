package com.capgemini.bankapplication.repository;




public interface BankApplicationRepository {
	
		public double getBalance(long accountId) ; 
		public boolean updateBalance(long accountId, double newBalance);


}
