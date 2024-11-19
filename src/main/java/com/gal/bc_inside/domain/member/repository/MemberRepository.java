package com.gal.bc_inside.domain.member.repository;

import com.gal.bc_inside.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByUsername(String username);
}
