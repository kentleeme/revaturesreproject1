package com.revature.exceptions;

public class RegisterUserFailedException extends RuntimeException {

	public RegisterUserFailedException(String string) {
		super(string);
	}

	public RegisterUserFailedException() {
	}

	public RegisterUserFailedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RegisterUserFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegisterUserFailedException(Throwable cause) {
		super(cause);
	}

	
}
