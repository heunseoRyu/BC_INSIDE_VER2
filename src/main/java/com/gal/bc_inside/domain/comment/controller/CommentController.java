package com.gal.bc_inside.domain.comment.controller;

import com.gal.bc_inside.domain.comment.dto.PostCommentRequest;
import com.gal.bc_inside.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
    public String addComment(@PathVariable Long boardId, @Valid @ModelAttribute("postCommentRequest") PostCommentRequest request, BindingResult result, Principal principal) {
        if(result.hasErrors()) {
            return "redirect:/boards/" + boardId;
        }
        try{
            commentService.postComment(boardId,request,principal);
        }catch(Exception e){
            result.reject("error", e.getMessage()); // 에러 처리
            return "redirect:/boards/" + boardId;
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
