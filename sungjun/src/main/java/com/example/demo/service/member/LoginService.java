package com.example.demo.service.member;

import com.example.demo.entity.member.LoginDto;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

	public void login(LoginDto loginDto);

}
