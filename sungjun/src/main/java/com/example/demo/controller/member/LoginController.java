package com.example.demo.controller.member;

import com.example.demo.entity.jwt.TokenInfo;
import com.example.demo.entity.member.LoginDto;
import com.example.demo.service.member.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		TokenInfo tokenInfo = loginService.login(loginDto);
		return ResponseEntity.ok(tokenInfo);
	}

}
