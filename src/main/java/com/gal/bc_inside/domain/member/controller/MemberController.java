package com.gal.bc_inside.domain.member.controller;

import com.gal.bc_inside.domain.member.entity.Member;
import com.gal.bc_inside.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
//
//    @GetMapping()
//    public String index() {
//        return "index";
//    }

    @GetMapping("/members")
    public String member(Model model, Principal principal){
        Member member = memberService.getMember(principal.getName());
        model.addAttribute(member);
        return "member";
    }
}