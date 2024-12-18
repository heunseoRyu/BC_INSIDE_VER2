package com.gal.bc_inside.domain.reply.controller;

import com.gal.bc_inside.domain.board.dto.BoardDetailViewDto;
import com.gal.bc_inside.domain.board.dto.PostBoardRequest;
import com.gal.bc_inside.domain.board.service.BoardService;
import com.gal.bc_inside.domain.comment.dto.PostCommentRequest;
import com.gal.bc_inside.domain.comment.service.CommentService;
import com.gal.bc_inside.domain.reply.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;
    private final BoardService boardService;
    private final CommentService commentService;

    @PostMapping("/boards/{boardId}/comments/{commentId}/reply")
    public String addReply(@PathVariable("commentId")Long commentId,@PathVariable("boardId")Long boardId, @Valid @ModelAttribute("postCommentRequest") PostCommentRequest request, BindingResult result, Principal principal,Model model) {
        if(result.hasErrors()) {
            // 유효성 검사 실패 시
            model.addAttribute("errorMessage", "입력값에 문제가 있습니다. \n 1. 댓글란을 입력한 후 '댓글 작성'을 눌러주세요 \n 2. 1~254이내로 적어주세요");
            model.addAttribute("url", "/boards/" + boardId + "/replies/" + commentId); // 돌아갈 URL
            return "/board/write_error";
        }
        try{
            replyService.postReply(boardId,commentId,request,principal);
        }catch(Exception e){
            model.addAttribute("errorMessage", "댓글 작성 중 문제가 발생했습니다: " + e.getMessage());
            model.addAttribute("url", "/boards/" + boardId + "/replies/" + commentId); // 돌아갈 URL
            return "/board/write_error";
        }

        return "redirect:/boards/" + boardId + "/replies/" + commentId;
    }

    @GetMapping("/boards/{boardId}/replies/{commentId}")
    public String getReplies(@PathVariable("boardId") Long boardId,@PathVariable("commentId") Long commentId, Authentication authentication, Model model){
        boolean isAdmin = false;
        if(authentication != null){
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        }
        model.addAttribute("isAdmin", isAdmin);
        BoardDetailViewDto board = boardService.getBoardDetail(boardId);
        model.addAttribute("boardDetail", board);
        model.addAttribute("comment",commentService.getComment(commentId));
        model.addAttribute("postCommentRequest",new PostBoardRequest());
        model.addAttribute("replies",replyService.getReplies(commentId));
        return "/board/detail_reply";
    }

    @GetMapping("/boards/{boardId}/replies/{id}/delete")
    public String deleteReply(@PathVariable("boardId")Long boardId, @PathVariable("id") Long replyId){
        Long commentId = replyService.getCommentIdByReply(replyId);
        replyService.deleteReply(replyId);
        return "redirect:/boards/" + boardId + "/replies/" + commentId;
    }
}
