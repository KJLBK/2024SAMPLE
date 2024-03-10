package com.example.demo.controller.member;

import com.example.demo.entity.member.Member;
import com.example.demo.entity.member.RegisterDto;
import com.example.demo.service.member.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

	private final RegisterService registerService;

	@PostMapping("/join")
	public ResponseEntity<?> register(@RequestBody RegisterDto registerDTO) {
		Member member = registerService.join(registerDTO);

		return ResponseEntity.ok(member);
	}

}
