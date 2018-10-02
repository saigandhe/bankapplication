package com.capgemini.bankapplication.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.capgemini.bankapplication.entities.BankApplication;
import com.capgemini.bankapplication.entities.Customer;
import com.capgemini.bankapplication.repository.CustomerRepository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Customer authenticate(Customer customer) throws DataAccessException {
		try {
		customer = jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = ? AND customer_password = ?" , new Object[] 
				{customer.getCustomerId(), customer.getCustomerPassword()}, new CustomerRowMapper());
		
		BankApplication app = jdbcTemplate.queryForObject("SELECT * FROM accounts WHERE account_id = (SELECT account_id FROM customers WHERE customer_id = ?)",
						new Object[] {customer.getCustomerId()}, new AccountRowMapper());
		customer.setAccount(app);
		return customer;
		} catch (DataAccessException e){
			e.initCause(new EmptyResultDataAccessException("Expected 1 actual 0", 1));
			throw e;
		}
	}

	@Override
	public Customer updateProfile(Customer customer) {
		jdbcTemplate.update("UPDATE customers SET customer_address = ? , customer_dob = ?, customer_emailid = ?, customer_name = ? WHERE customer_id =?", new Object[] 
				{customer.getCustomerAddress(),customer.getCustomerDateOfBirth(),customer.getCustomerEmailId(),customer.getCustomerName(),customer.getCustomerId()});
		customer = jdbcTemplate.queryForObject( "SELECT * FROM customers WHERE customer_id = ?", new Object[] {customer.getCustomerId()} , new CustomerRowMapper());
		return customer ;
	}

	@Override
	public boolean updatePassword(Customer customer, String oldPassword, String newPassword) {
		int count = jdbcTemplate.update("UPDATE customers SET customer_password = ? WHERE customer_id = ? AND customer_password = ?", new Object[]
				{newPassword,customer.getCustomerId(),oldPassword});
		
		return (count!=0)? true:false;
	}
	@Override
	public Customer updateSession(long customerId) {
		Customer customer = jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = ?",
				new Object[] {customerId}, new CustomerRowMapper());
		BankApplication bankApplication = jdbcTemplate.queryForObject(
				"SELECT * FROM accounts WHERE account_id = (SELECT account_id FROM customers WHERE customer_id = ?)",
				new Object[] {customerId}, new AccountRowMapper());
		customer.setAccount(bankApplication);
		
		return customer;
	}
	
	private class CustomerRowMapper implements RowMapper<Customer>{

		@Override
		public Customer mapRow(ResultSet rs, int rowNum)throws SQLException {
			
			Customer customer = new Customer();
			customer.setCustomerId(rs.getLong(1));
			customer.setCustomerName(rs.getString(2));
			customer.setCustomerPassword(rs.getString(3));
			customer.setCustomerEmailId(rs.getString(4));
			customer.setCustomerAddress(rs.getString(5));
			customer.setCustomerDateOfBirth(rs.getDate(6).toLocalDate());
			return customer;
		}

}
	
	private class AccountRowMapper implements RowMapper<BankApplication>{
		
		@Override
		public BankApplication mapRow(ResultSet rs, int rowNum)throws SQLException{
			BankApplication app = new BankApplication();
			app.setAccountId(rs.getLong(1));
			app.setAccountType(rs.getString(2));
			app.setBalance(rs.getDouble(3));
			return app;
		}
		
	}
}
