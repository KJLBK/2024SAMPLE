package com.example.demo.entity.member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Builder
public class Member {

	@Id
	private String id;

	private String pw;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String name;

	private String phone;

	private LocalDateTime joinDateTime;

	public static Member from(RegisterDto registerDto) {
		Member member = Member.builder()
				.id(registerDto.getId())
				.pw(registerDto.getPw())
				.name(registerDto.getName())
				.phone(registerDto.getPhone())
				.role(Role.USER)
				.joinDateTime(LocalDateTime.now())
				.build();

		return member;
	}

}
