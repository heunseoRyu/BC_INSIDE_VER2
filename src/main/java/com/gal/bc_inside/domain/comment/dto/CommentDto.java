package com.gal.bc_inside.domain.comment.dto;

import com.gal.bc_inside.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto {

    private Long id;

    private String content;

    private String username;

    public static CommentDto toDto(Comment entity){
        return new CommentDto(
                entity.getId(),
                entity.getContent(),
                entity.getMember().getUsername()
        );
    }

    public static List<CommentDto> of(List<Comment> comments) {
        return comments.parallelStream()
                .map(CommentDto::toDto)
                .toList();
    }
}
