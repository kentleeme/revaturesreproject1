package com.revature.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class UserNotFoundException extends RuntimeException {

	private static final Logger log = LoggerFactory.getLogger(UserNotFoundException.class);
	
	public UserNotFoundException() {
		super();
		MDC.put("statusCode","500");
		log.error("User not found");
	}

	public UserNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(String message) {
		super(message);
		MDC.put("statusCode","500");
		log.error(message);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}

	
}
