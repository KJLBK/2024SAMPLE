package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.JpaMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private JpaMemberRepository memberRepository;

    // 회원 가입
    public Optional<String> join(Member member){
        duplicationMember(member);
        passwordLimit(member.getMemberPassword());
        memberRepository.save(member);
        return Optional.ofNullable(member.getMemberEmail());
    }
    // 비밀 번호 길이 제한
    private void passwordLimit(String password){
        if(password.length() > 15){
            throw new IllegalStateException("비밀번호 길이가 잘못되었습니다.");
        }
    }
    // 중복 체크
    private void duplicationMember(Member member){
        memberRepository.findByMemberEmail(member.getMemberEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    // 로그인
    public Optional<String> login(String email, String password){
        Optional<Member> loginCheck = memberRepository.findByMemberEmail(email);
        if(loginCheck.isPresent()){
            Member member = loginCheck.get();
            if(member.getMemberPassword().equals(password)) return Optional.of(member.getMemberEmail());
        }
        return Optional.empty();
    }
}
