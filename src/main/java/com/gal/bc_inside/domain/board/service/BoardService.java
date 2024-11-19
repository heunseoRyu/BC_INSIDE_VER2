package com.gal.bc_inside.domain.board.service;

import com.gal.bc_inside.domain.board.dto.BoardDetailViewDto;
import com.gal.bc_inside.domain.board.dto.BoardDto;
import com.gal.bc_inside.domain.board.dto.BoardListViewDto;
import com.gal.bc_inside.domain.board.dto.PostBoardRequest;
import com.gal.bc_inside.domain.board.entity.Board;
import com.gal.bc_inside.domain.board.repository.BoardRepository;
import com.gal.bc_inside.domain.member.entity.Member;
import com.gal.bc_inside.domain.member.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    public Page<BoardListViewDto> getBoardList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")); // 한 페이지당 10개씩
        Page<Board> boards = boardRepository.findAll(pageable);

        return boards.map(BoardListViewDto::toDto);
    }

    public void postBoard(PostBoardRequest request, Principal principal){
        // 에러처리하기
        Member member = memberRepository.findByUsername(principal.getName()).orElse(null);

        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .bookTitle(request.getBookTitle())
                .bookUrl(request.getBookUrl())
                .member(member)
                .build();

        boardRepository.save(board);
    }

    public void deleteBoard(Long boardId){
        boardRepository.deleteById(boardId);
    }


    public BoardDetailViewDto getBoardDetail(Long id){
        Board board = boardRepository.findBoardById(id);
        return BoardDetailViewDto.toDto(board);
    }

    @Transactional
    public void editBoard(Long id,@Valid PostBoardRequest request, Principal principal) throws Exception{
        Member member = memberRepository.findByUsername(principal.getName()).orElse(null);
        Board board = boardRepository.findBoardById(id);
        // 1. 작성자가 맞는지
        if(!member.getUsername().equals(board.getMember().getUsername())){
            throw new Exception("당신은 작성자가 아니다.");
        }
        // 2. 수정
        board.update(
                request.getTitle(),
                request.getContent(),
                request.getBookTitle(),
                request.getBookUrl()
        );
    }
}
