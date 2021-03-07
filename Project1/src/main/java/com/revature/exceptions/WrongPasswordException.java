package com.revature.exceptions;

public class WrongPasswordException extends AuthorizationException {

	public WrongPasswordException() {
		super();
	}

	public WrongPasswordException(String message) {
		super(message);
	}

	public WrongPasswordException(Throwable cause) {
		super(cause);
	}

	public WrongPasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongPasswordException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
