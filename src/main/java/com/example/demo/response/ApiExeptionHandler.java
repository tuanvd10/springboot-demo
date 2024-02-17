package com.example.demo.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.exception.MyCustomException;

@RestControllerAdvice
public class ApiExeptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private ResponseHandler responseHandler;

	// custom exception
	@ExceptionHandler(MyCustomException.class)
	public ResponseEntity<Object> handleMyCustomException(MyCustomException ex) {
		System.out.println("MyCustomException");
		return responseHandler.createdFailedResponse(HttpStatus.FORBIDDEN, ex.getErrorCode(), ex.getMessage());
	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<Object> handleAuthenticationException(Exception ex) {
		return responseHandler.createdFailedResponse(HttpStatus.UNAUTHORIZED, 0, ex.getMessage());

	}
}
