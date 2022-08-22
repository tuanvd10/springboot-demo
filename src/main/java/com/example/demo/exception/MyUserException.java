package com.example.demo.exception;

public enum MyUserException {
	NOT_EXIST(10000, "record not exist", "record not exit description"),
	ALREADY_EXIST(10001, "record already exist", "record already exit description");

	private final MyCustomException myUserEx;

	private MyUserException(int errorCode, String message, String description) {
		this.myUserEx = new MyCustomException(errorCode, message, description);
	}

	public MyCustomException getException() {
		return myUserEx;
	};
}
