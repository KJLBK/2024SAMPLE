package com.example.demo.entity.jwt;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenInfo {

	private String accessToken;

	private String refreshToken;

}
