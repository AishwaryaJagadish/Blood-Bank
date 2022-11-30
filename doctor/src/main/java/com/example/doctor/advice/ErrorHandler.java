package com.example.doctor.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleContraintViolation(ConstraintViolationException e){		
		return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e){		
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	
}

// we use this instead of the try and catch blocks 
// when system crashes...it comes to controller advice and then this function gets executed and exception gets handled