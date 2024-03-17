package com.example.demo.controller.jwt;

import com.example.demo.entity.jwt.TokenInfo;
import com.example.demo.service.jwt.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JwtController {

	private final TokenService tokenService;

	@PostMapping("/token/refresh/check")
	public ResponseEntity<?> checkRefreshToken(HttpServletRequest request, HttpServletResponse response) {
		String refreshToken = tokenService.refreshTokenProcessing(request);
		TokenInfo token = tokenService.issueTokenByRefreshToken(refreshToken);
		tokenService.updateToken(response, token);
		return ResponseEntity.ok(token);
	}

}
