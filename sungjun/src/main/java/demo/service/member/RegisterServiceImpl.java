package demo.service.member;

import demo.entity.member.Member;
import demo.entity.member.RegisterDto;
import demo.exception.AlreadyUsedIdException;
import demo.exception.PasswordTooLongException;
import demo.exception.PasswordTooShortException;
import demo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

	private final MemberRepository memberRepository;

	@Override
	public Member join(RegisterDto registerDTO) {
		validateId(registerDTO.getId());
		validatePassword(registerDTO.getPw());

		Member member = Member.from(registerDTO);

		return memberRepository.save(member);
	}

	private boolean validateId(String id) {
		Optional<Member> member = memberRepository.findById(id);
		if (member.isEmpty()) {
			throw new AlreadyUsedIdException("이미 사용중인 아이디입니다.");
		}

		return false;
	}

	private void validatePassword(String password) {
		int passwordLength = password.length();
		if (passwordLength < 8) {
			throw new PasswordTooShortException("비밀번호가 8자보다 적습니다.");
		}

		if (passwordLength > 16) {
			throw new PasswordTooLongException("비밀번호가 16자를 초과합니다.");
		}
	}

}
