package com.example.demo.entity.member;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class MemberMapper {

	public static Member toEntity(RegisterDto registerDto) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		return Member.builder()
				.id(registerDto.getId())
				.pw(passwordEncoder.encode(registerDto.getPw()))
				.name(registerDto.getName())
				.phone(registerDto.getPhone())
				.role(Role.USER)
				.joinDateTime(LocalDateTime.now())
				.build();
	}

}
