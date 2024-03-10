package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

	ALREADY_USED_ID_EXCEPTION(HttpStatus.BAD_REQUEST,"M001", "이미 사용중인 아이디입니다."),
	PASSWORD_TOO_SHORT_EXCEPTION(HttpStatus.BAD_REQUEST, "M002", "비밀번호가 8자보다 적습니다."),
	PASSWORD_TOO_LONG_EXCEPTION(HttpStatus.BAD_REQUEST, "M003", "비밀번호가 16자를 초과합니다.");

	private final HttpStatus status;

	private final String code;

	private final String message;

	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

}
