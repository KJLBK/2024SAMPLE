package com.example.demo.entity.member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	@Id
	private String id;

	private String pw;

	@Enumerated(EnumType.STRING)
	@ColumnDefault("'USER'")
	private Role role;

	private String name;

	private String phone;

	private LocalDateTime joinDateTime;

	public static Member from(RegisterDto registerDto) {
		return Member.builder()
				.id(registerDto.getId())
				.pw(new BCryptPasswordEncoder().encode(registerDto.getPw()))
				.name(registerDto.getName())
				.phone(registerDto.getPhone())
				.role(Role.USER)
				.joinDateTime(LocalDateTime.now())
				.build();
	}

}
