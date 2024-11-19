package com.gal.bc_inside.domain.board.dto;

import com.gal.bc_inside.domain.board.entity.Board;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostBoardRequest {

    @NotEmpty(message = "제목을 입력하세요")
    @Size(min=1 ,max=255, message = "제목은 1~255자 이내입니다.")
    private String title;

    @NotEmpty(message = "책 이름을 입력하세요")
    @Size(min=1,max=255, message = "책이름은 1~255자 이내입니다.")
    private String bookTitle;

    @NotEmpty(message = "구매링크를 입력해주세요")
    @Size(min=1,max=255, message = "구매링크는 1~255자 이내입니다.")
    private String bookUrl;

    @NotEmpty(message = "내용을 입력해주세요")
    @Size(min=1,max=1000, message = "내용은 1~1000자 이내입니다.")
    private String content;

    public PostBoardRequest(BoardDetailViewDto board) {
        this.title = board.getTitle();
        this.bookTitle = board.getBookTitle();
        this.bookUrl = board.getBookUrl();
        this.content = board.getContent();
    }
}
