package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;
    // 회원가입
    @GetMapping("/members/createMember")
    public String showSignupForm(){
        return "/members/signup";
    }
    @PostMapping("/members/createMember")
    public String signup(@RequestBody Member member){
        Optional<String> sucess = memberService.join(member);
        if(sucess.isPresent()) return "redirect:/";
        else {
            return "/members/createMember";
        }
    }
    // 로그인
    @PostMapping("/members/loginMember")
    public String login(@RequestBody Member member, HttpSession session){
        Optional<String> sucess = memberService.login(member.getMemberEmail(), member.getMemberPassword());
        if(sucess.isPresent()){
            session.setAttribute("userEmail", member.getMemberEmail());
            return "redirect:/";
        }
        else {
            return "/members/loginMember";
        }
    }
    // 로그아웃
    @RequestMapping("/members/logoutMember")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
    // 회원탈퇴
    @PostMapping("/members/memberDelete")
    public String memberDelete(@RequestBody Member member, HttpSession session){
        memberService.memberDelete(member);
        return "redirect:/";
    }
}
