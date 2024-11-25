package com.gal.bc_inside.domain.board.controller;

import com.gal.bc_inside.domain.board.dto.BoardDetailViewDto;
import com.gal.bc_inside.domain.board.dto.BoardDto;
import com.gal.bc_inside.domain.board.dto.BoardListViewDto;
import com.gal.bc_inside.domain.board.dto.PostBoardRequest;
import com.gal.bc_inside.domain.board.entity.Board;
import com.gal.bc_inside.domain.board.service.BoardService;
import com.gal.bc_inside.domain.comment.dto.CommentDto;
import com.gal.bc_inside.domain.comment.dto.PostCommentRequest;
import com.gal.bc_inside.domain.comment.service.CommentService;
import com.gal.bc_inside.domain.reply.repository.ReplyRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

import static com.gal.bc_inside.domain.member.consts.AuthorityType.ADMIN;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/{id}")
    public String getBoardDetail(@PathVariable Long id, Authentication authentication, Model model) {
        boolean isAdmin = false;
        if(authentication != null){
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        }
        model.addAttribute("isAdmin", isAdmin);
        BoardDetailViewDto board = boardService.getBoardDetail(id);
        model.addAttribute("boardDetail", board);
        model.addAttribute("postCommentRequest",new PostCommentRequest());
        List<CommentDto> comments = commentService.getComments(id);
        model.addAttribute("comments",comments);
        model.addAttribute("replies",null);
        return "/board/detail";
    }

    @GetMapping("/{id}/ai")
    public String getSummarizedByAI(@PathVariable Long id, Authentication authentication, Model model) {
        boolean isAdmin = false;
        if(authentication != null){
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        }
        model.addAttribute("isAdmin", isAdmin);
        BoardDetailViewDto board = boardService.getBoardDetail(id);
        model.addAttribute("boardDetail", board);
        model.addAttribute("summarize",boardService.getSummarized(board));
        return "/board/detail_ai";
    }

    @PostMapping("") // 게시판 등록
    public String postBoard(@Valid @ModelAttribute("postBoardRequest") PostBoardRequest request, BindingResult result,Principal principal) {
        if(result.hasErrors()) {
            return "board/write";
        }
        try{
            boardService.postBoard(request,principal);
        }catch(Exception e){
            result.reject("error", e.getMessage());
            return "board/write";
        }
        return "redirect:/";
    }

    @PostMapping("/edit/{boardId}")
    public String updateBoard(@PathVariable("boardId") Long boardId, @Valid @ModelAttribute("postBoardRequest") PostBoardRequest request, BindingResult result,Principal principal) {
        if(result.hasErrors()) {
            return "board/edit";
        }
        try{
            boardService.editBoard(boardId,request,principal);
        }catch(Exception e){
            result.reject("error", e.getMessage());
            return "board/edit";
        }
        return "redirect:/boards/" + boardId;
    }

    @GetMapping("/delete/{id}")
    public String deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return "redirect:/";
    }
}