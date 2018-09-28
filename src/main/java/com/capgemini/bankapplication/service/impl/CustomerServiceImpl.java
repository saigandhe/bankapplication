package com.capgemini.bankapplication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.bankapplication.entities.Customer;
import com.capgemini.bankapplication.repository.impl.CustomerRepositoryImpl;
import com.capgemini.bankapplication.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepositoryImpl customerRepositoryImpl;
	@Override
	public Customer authenticate(Customer customer){
		return customerRepositoryImpl.authenticate(customer);
	}

	@Override
	public Customer updateProfile(Customer customer) {
		
		return customerRepositoryImpl.updateProfile(customer);
	}

	@Override
	public boolean updatePassword(Customer customer, String oldPassword, String newPassword) {
		
		return customerRepositoryImpl.updatePassword(customer, oldPassword, newPassword);
	}

	@Override
	public Customer updateSession(long customerId) {
		
		return customerRepositoryImpl.updateSession(customerId);
	}

}
