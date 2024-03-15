package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

	ALREADY_USED_ID_EXCEPTION(HttpStatus.BAD_REQUEST,"M001", "이미 사용중인 아이디입니다."),
	PASSWORD_TOO_SHORT_EXCEPTION(HttpStatus.BAD_REQUEST, "M002", "비밀번호가 8자보다 적습니다."),
	PASSWORD_TOO_LONG_EXCEPTION(HttpStatus.BAD_REQUEST, "M003", "비밀번호가 16자를 초과합니다."),
	NON_EXISTS_ACCOUNT_EXCEPTION(HttpStatus.BAD_REQUEST, "M004", "계정이 존재하지 않습니다."),

	WRONG_SECRET_KEY_EXCEPTION(HttpStatus.BAD_REQUEST, "T001 	", "비밀키가 잘못되었습니다."),
	EXPIRED_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, "T002", "토큰이 만료되었습니다."),
	UNVALID_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, "T003", "유효하지 않은 토큰입니다."),
	NOT_SURPPORTED_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, "T004", "지원되지 않는 토큰입니다.");

	private final HttpStatus status;

	private final String code;

	private final String message;

	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

}
