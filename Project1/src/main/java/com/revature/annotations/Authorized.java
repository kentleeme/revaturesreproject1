package com.revature.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.revature.models.UserRole;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Authorized {

	public UserRole[] allowedRoles() default {};
}
