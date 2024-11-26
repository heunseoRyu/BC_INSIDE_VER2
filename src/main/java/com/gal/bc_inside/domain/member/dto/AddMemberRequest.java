package com.gal.bc_inside.domain.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMemberRequest {

    @NotEmpty(message = "username을 입력해주세요")
    @Size(min=5, max=254, message = "username는 5자~254자로 설정해야 합니다")
    private String username;

    @NotEmpty(message = "password를 입력해주세요")
    @Size(min=5, max=20, message = "password는 5자~20자로 설정해야 합니다")
    private String password;
}