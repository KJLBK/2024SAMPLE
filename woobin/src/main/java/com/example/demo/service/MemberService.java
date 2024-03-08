package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.JpaMemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private JpaMemberRepository memberRepository;
    // 회원 가입
    public Optional<String> join(Member member){
        duplicationMember(member);
        memberRepository.save(member);
        return Optional.ofNullable(member.getMemberEmail());
    }
    // 중복 체크
    private void duplicationMember(Member member){
        memberRepository.findById(member.getMemberEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    // 로그인

}