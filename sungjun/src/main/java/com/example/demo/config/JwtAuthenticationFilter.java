package com.example.demo.config;

import com.example.demo.entity.jwt.TokenInfo;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.JwtCustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

	public static final String ACCESS_TOKEN_HEADER_NAME = "Authorization";

	public static final String AUTHENTICATION_TYPE = "Bearer ";

	private final JwtUtil jwtUtil;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Authentication authentication;

		String token = resolveToken((HttpServletRequest) request, ACCESS_TOKEN_HEADER_NAME);
		boolean tokenValidResult = jwtUtil.validateToken(token);

		if (tokenValidResult) {
			authentication = jwtUtil.getAuthentication(token);
			SecurityContextHolder.getContext()
					.setAuthentication(authentication);
		}

		if (!tokenValidResult) {
			String refreshToken = resolveToken((HttpServletRequest) request, ACCESS_TOKEN_HEADER_NAME);

			if (jwtUtil.validateToken(refreshToken)) {
				authentication = jwtUtil.getAuthentication(refreshToken);
				TokenInfo tokenInfo = jwtUtil.createToken(authentication);

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
			throw new JwtCustomException(ErrorCode.UNVALID_TOKEN_EXCEPTION);
		}

		if (!bearerToken.startsWith("Bearer")) {
			throw new JwtCustomException(ErrorCode.NOT_SURPPORTED_TOKEN_EXCEPTION);
		}

		return bearerToken.substring(7);  // bearer을 제외한 토큰 리턴
	}

}
