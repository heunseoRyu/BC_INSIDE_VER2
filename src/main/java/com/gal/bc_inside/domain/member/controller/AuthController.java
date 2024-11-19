package com.gal.bc_inside.domain.member.controller;


import com.gal.bc_inside.domain.member.dto.AddMemberRequest;
import com.gal.bc_inside.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/join")
    public String join( AddMemberRequest request) {
        return "join";
    }
    @PostMapping("/join")
    public String join(
            @Valid AddMemberRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "join";
        }
        try {
            memberService.addMember(request);
        } catch (DataIntegrityViolationException e) {
            result.reject("duplicated", "이미 사용 중인 username입니다.");
            return "join";
        } catch (Exception e) {
            result.reject("error", e.getMessage());
            return "join";
        }
        return "redirect:/";
    }

}

