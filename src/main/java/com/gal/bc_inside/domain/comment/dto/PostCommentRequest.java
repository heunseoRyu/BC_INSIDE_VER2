package com.gal.bc_inside.domain.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentRequest {

    @NotEmpty(message = "content를 입력해주세요")
    @Size(min=1, max=254, message = "내용은 1자~254자로 설정해야 합니다")
    private String content;
}
