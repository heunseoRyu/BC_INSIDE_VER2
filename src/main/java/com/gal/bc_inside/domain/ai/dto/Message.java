package com.gal.bc_inside.domain.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String role;
    private String content;

}
