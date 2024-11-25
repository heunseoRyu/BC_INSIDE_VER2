package com.gal.bc_inside.domain.reply.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddReplyRequest {
    private String content;

    private Long commentId;
}
