package com.gal.bc_inside.domain.reply.dto;

import com.gal.bc_inside.domain.comment.dto.CommentDto;
import com.gal.bc_inside.domain.comment.entity.Comment;
import com.gal.bc_inside.domain.reply.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyViewDto {
    private Long id;

    private String content;

    private String username;

    public static ReplyViewDto toDto(Reply entity){
        return new ReplyViewDto(
                entity.getId(),
                entity.getContent(),
                entity.getMember().getUsername()
        );
    }

    public static List<ReplyViewDto> of(List<Reply> comments) {
        return comments.parallelStream()
                .map(ReplyViewDto::toDto)
                .toList();
    }
}
