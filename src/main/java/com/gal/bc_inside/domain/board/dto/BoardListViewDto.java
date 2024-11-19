package com.gal.bc_inside.domain.board.dto;

import com.gal.bc_inside.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BoardListViewDto {

    private Long id;

    private String title;

    private String username;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    public static BoardListViewDto toDto(Board request) {

        return new BoardListViewDto(
                request.getId(),
                request.getTitle(),
                request.getMember().getUsername(),
                request.getCreatedDate(),
                request.getLastModifiedDate()
        );

    }
}
