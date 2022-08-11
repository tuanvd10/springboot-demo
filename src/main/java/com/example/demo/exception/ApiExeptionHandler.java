package com.example.demo.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.response.ResponseHandler;

@RestControllerAdvice
public class ApiExeptionHandler {
	@Autowired
	private ResponseHandler responseHandler;

	// custom exception
	@ExceptionHandler(MyCustomException.class)
	public ResponseEntity<Object> handleMyCustomException(MyCustomException ex) {
		System.out.println("MyCustomException");
		return responseHandler.createdFailedResponse(HttpStatus.BAD_REQUEST, ex.getErrorCode(), ex.getMessage());
	}
}
