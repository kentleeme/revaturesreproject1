package com.revature.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public abstract class AuthorizationException extends RuntimeException {

	private static final Logger log = LoggerFactory.getLogger(AuthorizationException.class);
	
	public AuthorizationException() {
		super();
		MDC.put("statusCode","500");
		log.error("Authorization Error");
	}

	public AuthorizationException(String message) {
		super(message);
		MDC.put("statusCode","500");
		log.error(message);
	}

	public AuthorizationException(Throwable cause) {
		super(cause);
	}

	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthorizationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
