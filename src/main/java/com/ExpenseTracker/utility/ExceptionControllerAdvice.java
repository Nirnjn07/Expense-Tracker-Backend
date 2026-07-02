package com.ExpenseTracker.utility;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;

import jakarta.validation.ConstraintViolation;

@ControllerAdvice
public class ExceptionControllerAdvice {
private static final org.apache.commons.logging.Log LOGGER = org.apache.commons.logging.LogFactory.getLog(ExceptionControllerAdvice.class);
@Autowired
private Environment environment;
@org.springframework.web.bind.annotation.ExceptionHandler(com.ExpenseTracker.exception.ExpenseTrackerException.class)
public org.springframework.http.ResponseEntity<ErrorInfo> meetingSchedulerExceptionHandler(com.ExpenseTracker.exception.ExpenseTrackerException exception)
{
	LOGGER.error(exception.getMessage(), exception);
	ErrorInfo errorInfo = new ErrorInfo();
	errorInfo.setErrorCode(org.springframework.http.HttpStatus.BAD_REQUEST.value());
	errorInfo.setErrorMessage(environment.getProperty(exception.getMessage()));
	return new org.springframework.http.ResponseEntity<>(errorInfo, org.springframework.http.HttpStatus.BAD_REQUEST);
}

@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
public org.springframework.http.ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exception)
{
	LOGGER.error(exception.getMessage(), exception);
	ErrorInfo errorInfo = new ErrorInfo();
	errorInfo.setErrorCode(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR.value());
	errorInfo.setErrorMessage(environment.getProperty("General.EXCEPTION_MESSAGE"));
	return new org.springframework.http.ResponseEntity<>(errorInfo,
			    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
}
@org.springframework.web.bind.annotation.ExceptionHandler({ org.springframework.web.bind.MethodArgumentNotValidException.class, jakarta.validation.ConstraintViolationException.class })
public org.springframework.http.ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception exception)
{
	LOGGER.error(exception.getMessage(), exception);
	String errorMsg;
	if (exception instanceof org.springframework.web.bind.MethodArgumentNotValidException)
	{
	    org.springframework.web.bind.MethodArgumentNotValidException manvException = (org.springframework.web.bind.MethodArgumentNotValidException) exception;

	errorMsg = manvException.getBindingResult()
		.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));}
	else
	{
	    errorMsg = ((jakarta.validation.ConstraintViolationException) exception).getConstraintViolations()
		    .stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
	}
	ErrorInfo errorInfo = new ErrorInfo();
	errorInfo.setErrorCode(org.springframework.http.HttpStatus.BAD_REQUEST.value());
	errorInfo.setErrorMessage(errorMsg);
	return new org.springframework.http.ResponseEntity<>(errorInfo, org.springframework.http.HttpStatus.BAD_REQUEST);
	}
}
