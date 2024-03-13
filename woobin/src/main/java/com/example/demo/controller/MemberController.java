package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
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
    @PostMapping("/members/loginMember")
    public String login(@RequestBody Member member){
        Optional<String> sucess = memberService.login(member.getMemberEmail(), member.getMemberPassword());
        if(sucess.isPresent()) return "redirect:/";
        else {
            return "/members/loginMember";
        }
    }
}
