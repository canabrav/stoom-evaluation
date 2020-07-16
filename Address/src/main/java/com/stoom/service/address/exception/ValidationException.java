package com.stoom.service.address.exception;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 5143407117775984876L;

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}
