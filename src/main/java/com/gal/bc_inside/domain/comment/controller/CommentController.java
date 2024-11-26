package com.gal.bc_inside.domain.comment.controller;

import com.gal.bc_inside.domain.comment.dto.PostCommentRequest;
import com.gal.bc_inside.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    public String addComment(@PathVariable Long boardId, @Valid @ModelAttribute("postCommentRequest") PostCommentRequest request, BindingResult result, Principal principal, Model model) {
        if(result.hasErrors()) {
            // 유효성 검사 실패 시
            model.addAttribute("errorMessage", "입력값에 문제가 있습니다. 댓글을 작성하거나 1~254이내로 적어주세요");
            model.addAttribute("url", "/boards/" + boardId); // 돌아갈 URL
            return "/board/write_error";
        }
        try{
            commentService.postComment(boardId,request,principal);
        }catch(Exception e){
            // 예외 발생 시 에러 메시지 설정
            model.addAttribute("errorMessage", "댓글 작성 중 문제가 발생했습니다: " + e.getMessage());
            model.addAttribute("url", "/boards/" + boardId); // 돌아갈 URL
            return "/board/write_error";
        }

        return "redirect:/boards/" + boardId;
    }

    @GetMapping("/{id}/delete")
    public String deleteComment(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long commentId) {

        commentService.deleteComment(commentId);

        return "redirect:/boards/" + boardId;
    }

}
