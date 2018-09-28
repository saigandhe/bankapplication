package com.capgemini.bankapplication.repository;

import com.capgemini.bankapplication.entities.Customer;

public interface CustomerRepository {
	
	public Customer authenticate(Customer customer);
	public Customer updateProfile(Customer customer);
	public boolean updatePassword(Customer customer, String oldPassword, String newPassword);
	public Customer updateSession(long customerId);
}
