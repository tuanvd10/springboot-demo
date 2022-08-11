package com.example.demo.exception;

public class MyCustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String description;

	public MyCustomException(int errorCode, String message, String description) {
		super(message);
		this.errorCode = errorCode;
		this.description = description;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
