package com.gal.bc_inside.domain.member.dto;

import com.gal.bc_inside.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddMemberResponse {

    private String username;

    public  AddMemberResponse(Member member) {
        this.username = member.getUsername();
    }
}
