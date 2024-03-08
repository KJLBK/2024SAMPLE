package com.example.demo.entity.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDto {

	private String id;

	private String pw;

	private String name;

	private String phone;

}
