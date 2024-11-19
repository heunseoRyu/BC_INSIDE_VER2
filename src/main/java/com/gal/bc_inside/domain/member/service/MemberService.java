package com.gal.bc_inside.domain.member.service;

import com.gal.bc_inside.domain.member.consts.AuthorityType;
import com.gal.bc_inside.domain.member.dto.AddMemberRequest;
import com.gal.bc_inside.domain.member.dto.AddMemberResponse;
import com.gal.bc_inside.domain.member.entity.Member;
import com.gal.bc_inside.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public AddMemberResponse addMember(AddMemberRequest request){
        Member member = new Member(request.getUsername(), passwordEncoder.encode(request.getPassword()), AuthorityType.USER);
        return new AddMemberResponse(memberRepository.save(member));
    }

    public Member getMember(String username) {
        return memberRepository.findByUsername(username).orElse(null);
    }
}
