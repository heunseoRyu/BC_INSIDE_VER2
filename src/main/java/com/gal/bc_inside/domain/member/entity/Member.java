package com.gal.bc_inside.domain.member.entity;

import com.gal.bc_inside.domain.member.consts.AuthorityType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "tb_member")
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthorityType authority;
}
