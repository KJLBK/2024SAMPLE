package com.example.demo.service.member;

import com.example.demo.entity.member.Member;
import com.example.demo.entity.member.MemberMapper;
import com.example.demo.entity.member.RegisterDto;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.MemberException;
import com.example.demo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

	private final MemberRepository memberRepository;

	@Override
	public Member join(RegisterDto registerDto) {
		validateId(registerDto.getId());
		validatePassword(registerDto.getPw());

		Member member = MemberMapper.toEntity(registerDto);
		return memberRepository.save(member);
	}

	private void validateId(String id) {
		Optional<Member> member = memberRepository.findById(id);

		if (member.isPresent()) {
			throw new MemberException(ErrorCode.ALREADY_USED_ID_EXCEPTION);
		}
	}

	private void validatePassword(String password) {
		int passwordLength = password.length();

		if (passwordLength < 8) {
			throw new MemberException(ErrorCode.PASSWORD_TOO_SHORT_EXCEPTION);
		}

		if (passwordLength > 16) {
			throw new MemberException(ErrorCode.PASSWORD_TOO_LONG_EXCEPTION);
		}
	}

}
