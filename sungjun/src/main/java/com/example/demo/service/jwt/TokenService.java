package com.example.demo.service.jwt;

import com.example.demo.entity.jwt.TokenInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface TokenService {

	public void updateToken(HttpServletResponse response, TokenInfo token);

	public String refreshTokenProcessing(HttpServletRequest request);

	public TokenInfo issueTokenByRefreshToken(String refreshToken);

}
