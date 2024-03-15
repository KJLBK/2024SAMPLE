package com.example.demo.service.jwt;

import com.example.demo.entity.member.Member;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.MemberException;
import com.example.demo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> member = memberRepository.findById(username);

		if (member.isEmpty()) {
			throw new MemberException(ErrorCode.NON_EXISTS_ACCOUNT_EXCEPTION);
		}

		return member.get()
				.getUserDetails();
	}

}
