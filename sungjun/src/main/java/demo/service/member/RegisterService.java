package demo.service.member;

import demo.entity.member.Member;
import demo.entity.member.RegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface RegisterService {

	public Member join(RegisterDto registerDTO);
}
