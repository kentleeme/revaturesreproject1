package com.revature.advices;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.annotations.Authorized;
import com.revature.exceptions.NotAuthorizedException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.UserRole;
import com.revature.models.User;

@Aspect
@Component
public class AuthAspect {

	@Autowired
	private HttpServletRequest req;
	
	
	@Around("@annotation(authorized)")
	public Object authenticate(ProceedingJoinPoint pjp, Authorized authorized) throws Throwable {
		
		HttpSession session = req.getSession(false);
		
		if(session == null || session.getAttribute("currentUser") == null) {
			throw new NotLoggedInException("Must be logged in to perform this action");
		}
		
		
		User currentUser = (User) session.getAttribute("currentUser");
		UserRole currentRole = currentUser.getRole();
		
		List<UserRole> allowedRoles = Arrays.asList(authorized.allowedRoles());
		
		if(!allowedRoles.contains(currentRole)) {
			
			throw new NotAuthorizedException("You are not authorized to perform this action");
		}
		
		return pjp.proceed(pjp.getArgs());
	}
}