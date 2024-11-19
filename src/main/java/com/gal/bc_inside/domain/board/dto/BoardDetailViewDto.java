package com.gal.bc_inside.domain.board.dto;

import com.gal.bc_inside.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BoardDetailViewDto {

    private Long id;

    private String title;

    private String username;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private String bookTitle;

    private String bookUrl;

    private String content;

    public static BoardDetailViewDto toDto(Board request) {

        return new BoardDetailViewDto(
                request.getId(),
                request.getTitle(),
                request.getMember().getUsername(),
                request.getCreatedDate(),
                request.getLastModifiedDate(),
                request.getBookTitle(),
                request.getBookUrl(),
                request.getContent()
        );

    }
}
