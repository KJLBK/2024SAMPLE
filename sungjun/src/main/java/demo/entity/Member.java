package demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Member {

	@Id
	private String id;

	private String pw;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String name;

	private String phone;

	private LocalDateTime joinDateTime;
}
