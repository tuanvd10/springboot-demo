package com.example.demo.exception;

public enum AuthException {
	PARAM_WRONG(10000, "wrong params", "param wrong"),
	ALREADY_EXIST(10001, "record already exist", "record already exit description");

	private final MyCustomException authEx;

	private AuthException(int errorCode, String message, String description) {
		this.authEx = new MyCustomException(errorCode, message, description);
	}

	public MyCustomException getException() {
		return authEx;
	};

}
