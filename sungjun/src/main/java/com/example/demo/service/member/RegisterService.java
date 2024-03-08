package com.example.demo.service.member;

import com.example.demo.entity.member.Member;
import com.example.demo.entity.member.RegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface RegisterService {

	public Member join(RegisterDto registerDTO);

}
