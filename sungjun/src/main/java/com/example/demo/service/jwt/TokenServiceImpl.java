package com.example.demo.service.jwt;

import com.example.demo.config.JwtUtil;
import com.example.demo.entity.jwt.TokenInfo;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.JwtCustomException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	@Value("${jwt.expired-time.access-token}")
	private long accessTokenExpiredTime;

	@Value("${jwt.expired-time.refresh-token}")
	private long refreshTokenExpiredTime;

	@Value("${jwt.cookie.name.access}")
	private String accessTokenCookieName;

	@Value("${jwt.cookie.name.refresh}")
	private String refreshTokenCookieName;

	private final JwtUtil jwtUtil;

	@Override
	public void updateToken(HttpServletResponse response, TokenInfo token) {
		setCookie(response, accessTokenCookieName, token.getAccessToken(), accessTokenExpiredTime);
		setCookie(response, refreshTokenCookieName, token.getAccessToken(), refreshTokenExpiredTime);
	}

	@Override
	public String refreshTokenProcessing(HttpServletRequest request) {
		Cookie cookie = Arrays.stream(request.getCookies())
				.filter(v -> v.getName()
						.equals("refresh-token"))
				.findFirst()
				.orElse(null);

		if (cookie == null) {
			throw new JwtCustomException(ErrorCode.NOT_EXISTS_TOKEN_EXCEPTION);
		}

		return cookie.getValue();
	}

	@Override
	public TokenInfo issueTokenByRefreshToken(String refreshToken) {
		Claims claims = jwtUtil.getClaims(refreshToken);
		Authentication authentication = jwtUtil.getAuthentication(claims);
		return jwtUtil.createToken(authentication);
	}

	private void setCookie(HttpServletResponse response, String cookieName, String token, long expiredTime) {
		ResponseCookie cookie = ResponseCookie.from(cookieName, token)
				.maxAge(expiredTime)
				.path("/")
				.secure(true)
				.httpOnly(true)
				.build();
		response.setHeader("Set-Cookie", cookie.toString());
	}

}
