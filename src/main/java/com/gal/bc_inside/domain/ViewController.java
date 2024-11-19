package com.gal.bc_inside.domain;

import com.gal.bc_inside.domain.board.dto.BoardListViewDto;
import com.gal.bc_inside.domain.board.dto.PostBoardRequest;
import com.gal.bc_inside.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0") int page,Model model) {
        model.addAttribute("boards", "");
        Page<BoardListViewDto> boardList = boardService.getBoardList(page);
        model.addAttribute("boardList", boardList);
        model.addAttribute("currentPage", page);
        return "index";
    }

    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("postBoardRequest", new PostBoardRequest()); // 빈 객체 추가
        return "/board/write";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id",id);
        model.addAttribute("postBoardRequest", new PostBoardRequest(boardService.getBoardDetail(id)));
        return "/board/edit";
    }

}
