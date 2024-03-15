package com.example.demo.exception;

public class JwtCustomException extends RuntimeException {

	private final ErrorCode errorCode;

	public JwtCustomException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

}
