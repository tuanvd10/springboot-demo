package com.example.demo.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.response.ResponseHandler;

@RestControllerAdvice
public class SystemExceptionHandler {

	@Autowired
	private ResponseHandler responseHandler;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllException(Exception ex) {
		System.out.println("handleAllException");
		ex.printStackTrace();
		return responseHandler.createdFailedResponse(HttpStatus.INTERNAL_SERVER_ERROR, 100, ex.getLocalizedMessage());
	}

	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity<Object> handleASpecificException(IndexOutOfBoundsException ex) {
		System.out.println("IndexOutOfBoundsException");
		return responseHandler.createdFailedResponse(HttpStatus.BAD_REQUEST, 100, ex.getLocalizedMessage());
	}
}
