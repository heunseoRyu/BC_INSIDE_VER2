package com.gal.bc_inside.domain.board.service;

import com.gal.bc_inside.domain.ai.service.ChatGptService;
import com.gal.bc_inside.domain.board.dto.BoardDetailViewDto;
import com.gal.bc_inside.domain.board.dto.BoardListViewDto;
import com.gal.bc_inside.domain.board.dto.PostBoardRequest;
import com.gal.bc_inside.domain.board.entity.Board;
import com.gal.bc_inside.domain.board.repository.BoardRepository;
import com.gal.bc_inside.domain.comment.entity.Comment;
import com.gal.bc_inside.domain.comment.repository.CommentRepository;
import com.gal.bc_inside.domain.member.entity.Member;
import com.gal.bc_inside.domain.member.repository.MemberRepository;
import com.gal.bc_inside.domain.reply.entity.Reply;
import com.gal.bc_inside.domain.reply.repository.ReplyRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final ChatGptService chatGptService;


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
        replyRepository.deleteByBoard_Id(boardId);
        commentRepository.deleteByBoard_Id(boardId);
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

    public String getSummarized(BoardDetailViewDto board) {
        List<String> comments = commentRepository.findContentsByBoard_IdOrderByIdAsc(board.getId());
        List<String> replies = replyRepository.findContentsByBoard_IdOrderByIdAsc(board.getId());
        String prompt = "너는 독서 토의 커뮤니티의 게시글에서 사용하는 기능을 수행할거야. 3가지를 해야돼." +
                "1.책의 줄거리를 요약하고, 2. 본문의 주장을 요약 3. 댓글,대댓글을 바탕으로 여론 요약 \n";
        prompt += """
                형식은 이렇게 해줘
                1. 줄거리 요약 \n
                2. 본문 주장 요약 \n
                3. 댓글, 대댓글을 바탕으로 여론 요약 \n
                """;
        prompt += String.format("자료 1. 줄거리는 %s의 줄거리를 요약해줘. 구매링크 : %s \n",board.getBookTitle(),board.getBookUrl());
        prompt += String.format("자료 2. 본문 \n %s \n",board.getContent());
        prompt += String.format("자료 3. 댓글, 대댓글 리스트 값 %s",comments.toString()+replies.toString());
        System.out.println(prompt);

        String response = chatGptService.getResponse(prompt);
        System.out.println(response);
        return response;
    }
}
