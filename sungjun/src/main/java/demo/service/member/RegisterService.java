package demo.service.member;

import demo.entity.member.RegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface RegisterService {

	public boolean join(RegisterDto registerDTO);
}
