package com.example.demo.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler {
	public static final String SUCCESS_MESSAGE = "SUCCESS";

	public ResponseEntity<Object> createdSuccessResponse() {
		return new ResponseEntity<>(new SuccessMessage(SUCCESS_MESSAGE), HttpStatus.OK);
	}

	public ResponseEntity<Object> createdSuccessResponse(Object data) {
		return new ResponseEntity<>(new SuccessMessage(data), HttpStatus.OK);
	}

	public ResponseEntity<Object> createdSuccessResponse(Object data, int total) {
		return new ResponseEntity<>(new SuccessMessage(data, total), HttpStatus.OK);
	}

	public ResponseEntity<Object> createdFailedResponse(HttpStatus httpStatus, int error_code, String message) {
		return new ResponseEntity<>(new ErrorMessage(httpStatus.value(), error_code, message), httpStatus);
	}
}
