package com.gal.bc_inside.domain.member.service;

import com.gal.bc_inside.domain.member.entity.Member;
import com.gal.bc_inside.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다")) ;

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getAuthority().name()));
        System.out.println(authorities);

        return new User(member.getUsername(), member.getPassword(), authorities);
    }
}
