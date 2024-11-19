package com.gal.bc_inside.domain.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDto {

    private String title;

    private String username;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private String bookTitle;

    private String bookUrl;

    private String content;
}
