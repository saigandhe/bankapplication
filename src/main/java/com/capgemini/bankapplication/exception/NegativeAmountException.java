package com.capgemini.bankapplication.exception;

public class NegativeAmountException extends RuntimeException {
	public NegativeAmountException(String message) {
		super(message);
	}

}
