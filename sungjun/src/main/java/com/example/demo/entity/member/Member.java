package com.example.demo.entity.member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

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

	public UserDetails getUserDetails() {
		Collection<? extends GrantedAuthority> auth = Arrays.asList(this.role.toString())
				.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		return User.builder()
				.username(this.id)
				.password(this.pw)
				.authorities(auth)
				.build();
	}

}
