package com.gal.bc_inside.domain.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMemberRequest {

    @NotEmpty(message = "username을 입력해주세요")
    private String username;

    @NotEmpty(message = "password를 입력해주세요")
    @Size(min=8, max=20, message = "password는 8자~20자로 설정해야 합니다")
    private String password;
}