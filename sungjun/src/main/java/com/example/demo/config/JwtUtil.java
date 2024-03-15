package com.example.demo.config;

import com.example.demo.entity.jwt.TokenInfo;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.JwtCustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

	private static final String ROLE_NAME = "roles";

	private final UserDetailsService userDetailsService;

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.expired-time.access-token}")
	private long accessTokenExpiredTime;

	@Value("${jwt.expired-time.refresh-token}")
	private long refreshTokenExpiredTime;

	public TokenInfo createToken(Authentication authentication) {
		Claims claims = Jwts.claims()
				.setSubject(authentication.getName());
		claims.put(ROLE_NAME, authentication.getAuthorities());

		Date now = new Date();
		Date accessTokenExpired = new Date(now.getTime() + accessTokenExpiredTime);
		Date refreshTokenExpired = new Date(now.getTime() + refreshTokenExpiredTime);

		String accessToken = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(accessTokenExpired)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();

		String refreshToken = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(refreshTokenExpired)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();

		return TokenInfo.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

	public boolean validateToken(String token) {
		if (token == null || token.isEmpty()) {
			return false;
		}

		Claims claims = getClaims(token);
		return !claims.getExpiration().before(new Date());
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(getMemberId(token));

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private String getMemberId(String token) {
		return getClaims(token).getSubject();
	}

	private Claims getClaims(String token) {
		Claims claims;

		try {
			claims = Jwts.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(token)
					.getBody();
		} catch (SignatureException e) {
			throw new JwtCustomException(ErrorCode.WRONG_SECRET_KEY_EXCEPTION);
		} catch (ExpiredJwtException e) {
			throw new JwtCustomException(ErrorCode.EXPIRED_TOKEN_EXCEPTION);
		} catch (MalformedJwtException e) {
			throw new JwtCustomException(ErrorCode.UNVALID_TOKEN_EXCEPTION);
		} catch (UnsupportedJwtException e) {
			throw new JwtCustomException(ErrorCode.NOT_SURPPORTED_TOKEN_EXCEPTION);
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException("잘못된 입력값", e);
		}

		return claims;
	}

}
