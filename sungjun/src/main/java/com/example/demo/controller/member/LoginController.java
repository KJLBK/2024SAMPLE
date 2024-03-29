package com.example.demo.controller.member;

import com.example.demo.entity.jwt.TokenInfo;
import com.example.demo.entity.member.LoginDto;
import com.example.demo.service.jwt.TokenService;
import com.example.demo.service.member.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	private final TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
		TokenInfo token = loginService.login(loginDto);
		tokenService.updateToken(response, token);
		return ResponseEntity.ok(token);
	}

}
