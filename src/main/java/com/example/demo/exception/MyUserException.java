package com.example.demo.exception;

public enum MyUserException {
	NOT_EXIST(10000, "user not exist", "user not exit description");

	private final MyCustomException myUserEx;

	private MyUserException(int errorCode, String message, String description) {
		this.myUserEx = new MyCustomException(errorCode, message, description);
	}

	public MyCustomException getException() {
		return myUserEx;
	};
}
