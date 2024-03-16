package com.example.demo.config;

import com.example.demo.entity.jwt.TokenInfo;
import com.example.demo.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

	private static final String ROLE_NAME = "roles";

	private final UserDetailsService userDetailsService;

	@Value("${jwt.secret-key}")
	private String secretKey;

	private Key key;

	@Value("${jwt.expired-time.access-token}")
	private long accessTokenExpiredTime;

	@Value("${jwt.expired-time.refresh-token}")
	private long refreshTokenExpiredTime;

	@PostConstruct
	private void init() {
		byte[] decodedKey = Base64.getDecoder().decode(this.secretKey);
		this.key = Keys.hmacShaKeyFor(decodedKey);
	}

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
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();

		String refreshToken = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(refreshTokenExpired)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();

		return TokenInfo.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

	public boolean validateToken(Claims claims) {
		if (claims == null) {
			return false;
		}

		return !claims.getExpiration().before(new Date());
	}

	public Authentication getAuthentication(Claims claims) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(getMemberId(claims));

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private String getMemberId(Claims claims) {
		return claims.getSubject();
	}

	public Claims getClaims(String token) {
		Claims claims = null;

		try {
			claims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (SignatureException e) {
			log.info(ErrorCode.WRONG_SECRET_KEY_EXCEPTION.getMessage());
		} catch (ExpiredJwtException e) {
			log.info(ErrorCode.EXPIRED_TOKEN_EXCEPTION.getMessage());
		} catch (MalformedJwtException e) {
			log.info(ErrorCode.UNVALID_TOKEN_EXCEPTION.getMessage());
		} catch (UnsupportedJwtException e) {
			log.info(ErrorCode.NOT_SURPPORTED_TOKEN_EXCEPTION.getMessage());
		} catch (IllegalArgumentException e) {
			log.info(String.valueOf(e));
		}

		return claims;
	}

}
