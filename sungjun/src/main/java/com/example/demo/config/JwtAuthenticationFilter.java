package com.example.demo.config;

import com.example.demo.entity.jwt.TokenInfo;
import com.example.demo.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

	public static final String ACCESS_TOKEN_HEADER_NAME = "Authorization";

	public static final String AUTHENTICATION_TYPE = "Bearer ";

	private final JwtUtil jwtUtil;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String accessToken = resolveToken((HttpServletRequest) request, ACCESS_TOKEN_HEADER_NAME);
		if (accessToken == null) {
			chain.doFilter(request, response);
			return;
		}

		Claims accessTokenClaims = jwtUtil.getClaims(accessToken);

		boolean tokenValidResult = jwtUtil.validateToken(accessTokenClaims);

		if (tokenValidResult) {
			Authentication AuthenticationByAccessToken = jwtUtil.getAuthentication(accessTokenClaims);
			SecurityContextHolder.getContext()
					.setAuthentication(AuthenticationByAccessToken);
		}

		if (!tokenValidResult) {
			String refreshToken = resolveToken((HttpServletRequest) request, ACCESS_TOKEN_HEADER_NAME);
			Claims refreshTokenClaims = jwtUtil.getClaims(refreshToken);

			if (jwtUtil.validateToken(refreshTokenClaims)) {
				Authentication AuthenticationByRefreshToken = jwtUtil.getAuthentication(refreshTokenClaims);
				TokenInfo tokenInfo = jwtUtil.createToken(AuthenticationByRefreshToken);

				HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				httpServletResponse.setHeader(ACCESS_TOKEN_HEADER_NAME, AUTHENTICATION_TYPE + tokenInfo);
			}
		}

		chain.doFilter(request, response);
	}


	/** Request Header 에서 토큰 추출 */
	private String resolveToken(HttpServletRequest request, String headerName) {
		String bearerToken = request.getHeader(headerName);  // 서블릿 헤더에서 Authorization 값 가져오기

		if (bearerToken == null || bearerToken.isEmpty()) {
			log.info(String.valueOf(ErrorCode.NOT_EXISTS_TOKEN_EXCEPTION));
			return null;
		}

		if (!bearerToken.startsWith("Bearer")) {
			log.info(String.valueOf(ErrorCode.NOT_SURPPORTED_TOKEN_EXCEPTION));
			return null;
		}

		return bearerToken.substring(7);  // bearer을 제외한 토큰 리턴
	}

}
