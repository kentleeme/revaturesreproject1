package com.revature.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.revature.services.UserService;

public class CourseNotFoundException extends RuntimeException {

	private static final Logger log = LoggerFactory.getLogger(CourseNotFoundException.class);
	
	public CourseNotFoundException() {
		super();
		MDC.put("statusCode","500");
		log.error("Course Not Found");
	}

	public CourseNotFoundException(String message) {
		super(message);
		MDC.put("statusCode","500");
		log.error(message);
	}

	public CourseNotFoundException(Throwable cause) {
		super(cause);
	}

	public CourseNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CourseNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
