package com.gal.bc_inside.domain.comment.service;

import com.gal.bc_inside.domain.board.entity.Board;
import com.gal.bc_inside.domain.board.repository.BoardRepository;
import com.gal.bc_inside.domain.comment.dto.CommentDto;
import com.gal.bc_inside.domain.comment.dto.PostCommentRequest;
import com.gal.bc_inside.domain.comment.entity.Comment;
import com.gal.bc_inside.domain.comment.repository.CommentRepository;
import com.gal.bc_inside.domain.member.entity.Member;
import com.gal.bc_inside.domain.member.repository.MemberRepository;
import com.gal.bc_inside.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public void postComment(Long boardId,PostCommentRequest request, Principal principal){
        Member member = memberRepository.findByUsername(principal.getName()).orElse(null);
        Board board = boardRepository.findBoardById(boardId);

        System.out.println(request.getContent());

        Comment comment = Comment.builder()
                .content(request.getContent())
                .member(member)
                .board(board)
                .build();

        commentRepository.save(comment);
    }

    public List<CommentDto> getComments(Long boardId){
        List<CommentDto> comments = CommentDto.of(commentRepository.findAllByBoard_IdOrderByIdDesc(boardId));
        return comments;
    }

    public void deleteComment(Long id){
        replyRepository.deleteByComment_Id(id);
        commentRepository.deleteById(id);
    }

    public CommentDto getComment(Long commentId) {
        CommentDto comment = CommentDto.toDto(commentRepository.findById(commentId).get());
        return comment;
    }
}
