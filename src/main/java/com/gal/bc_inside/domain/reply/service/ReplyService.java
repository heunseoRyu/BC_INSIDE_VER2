package com.gal.bc_inside.domain.reply.service;

import com.gal.bc_inside.domain.board.entity.Board;
import com.gal.bc_inside.domain.board.repository.BoardRepository;
import com.gal.bc_inside.domain.comment.dto.PostCommentRequest;
import com.gal.bc_inside.domain.comment.entity.Comment;
import com.gal.bc_inside.domain.comment.repository.CommentRepository;
import com.gal.bc_inside.domain.member.entity.Member;
import com.gal.bc_inside.domain.member.repository.MemberRepository;
import com.gal.bc_inside.domain.reply.dto.ReplyViewDto;
import com.gal.bc_inside.domain.reply.entity.Reply;
import com.gal.bc_inside.domain.reply.repository.ReplyRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public void deleteReply(Long id){
        replyRepository.deleteById(id);
    }

    public List<ReplyViewDto> getReplies(Long commentId){
        List<ReplyViewDto> replies = ReplyViewDto.of(replyRepository.findAllByComment_IdOrderByIdAsc(commentId));
        return replies;
    }

    public void postReply(Long boardId,Long commentId, @Valid PostCommentRequest request, Principal principal) {
        Member member = memberRepository.findByUsername(principal.getName()).orElse(null);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        Board board = boardRepository.findById(boardId).orElse(null);

        System.out.println(request.getContent());

        Reply reply = Reply.builder()
                .content(request.getContent())
                .member(member)
                .comment(comment)
                .board(board)
                .build();

        replyRepository.save(reply);
    }

    public Long getCommentIdByReply(Long id) {
        return replyRepository.findById(id).get().getComment().getId();
    }
}
