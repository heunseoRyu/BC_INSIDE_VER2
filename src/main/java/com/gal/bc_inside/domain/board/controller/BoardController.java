package com.gal.bc_inside.domain.board.controller;

import com.gal.bc_inside.domain.board.dto.BoardDetailViewDto;
import com.gal.bc_inside.domain.board.dto.BoardDto;
import com.gal.bc_inside.domain.board.dto.BoardListViewDto;
import com.gal.bc_inside.domain.board.dto.PostBoardRequest;
import com.gal.bc_inside.domain.board.entity.Board;
import com.gal.bc_inside.domain.board.service.BoardService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{id}")
    public String getBoardDetail(@PathVariable Long id, Model model) {
        BoardDetailViewDto board = boardService.getBoardDetail(id);
        model.addAttribute("boardDetail", board);
        return "/board/detail";
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
        System.out.println("entered");
        boardService.deleteBoard(id);
        return "redirect:/";
    }
}