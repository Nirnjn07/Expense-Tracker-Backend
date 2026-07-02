package com.ExpenseTracker.utility;
@org.aspectj.lang.annotation.Aspect
@org.springframework.stereotype.Component
public class LoggingAspect {

	private static final org.apache.commons.logging.Log LOGGER = org.apache.commons.logging.LogFactory.getLog(LoggingAspect.class);

	@org.aspectj.lang.annotation.AfterThrowing(pointcut = "execution(* com.ExpenseTracker.service.*Impl.*(..))", throwing = "exception")
	public void logServiceException(Exception exception) {
		LOGGER.error(exception.getMessage(), exception);
	}
}
