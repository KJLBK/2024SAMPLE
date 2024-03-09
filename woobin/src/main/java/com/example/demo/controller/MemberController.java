package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String signup(Member member, Model model){
        Member user = member;
        Optional<String> sucess = memberService.join(user);
        if(sucess.isPresent()) return "redirect:/";
        else {
            model.addAttribute("error", "회원가입에 실패하였습니다.");
            return "/members/createMember";
        }
    }
}
