package com.example.demo.service.member;

import com.example.demo.config.JwtUtil;
import com.example.demo.entity.jwt.TokenInfo;
import com.example.demo.entity.member.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final JwtUtil jwtUtil;

	@Override
	public TokenInfo login(LoginDto loginDto) {
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(loginDto.getId(), loginDto.getPw());

		Authentication authentication = authenticationManagerBuilder.getObject()
				.authenticate(authenticationToken);

		SecurityContextHolder.getContext()
				.setAuthentication(authentication);

		return jwtUtil.createToken(authentication);
	}

}
