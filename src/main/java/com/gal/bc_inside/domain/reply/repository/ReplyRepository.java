package com.gal.bc_inside.domain.reply.repository;

import com.gal.bc_inside.domain.comment.dto.CommentDto;
import com.gal.bc_inside.domain.comment.entity.Comment;
import com.gal.bc_inside.domain.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByComment_IdOrderByIdAsc(Long commentId);

    @Transactional
    @Modifying
    @Query(value="delete from tb_reply where fk_board_id = :boardId",nativeQuery = true)
    void deleteByBoard_Id(@Param("boardId") Long boardId);

    @Transactional
    @Modifying
    @Query(value="delete from tb_reply where fk_comment_id = :commentId",nativeQuery = true)
    void deleteByComment_Id(@Param("commentId") Long commentId);

    @Query("select r.content from Reply r where r.board.id = :id order by r.id asc")
    List<String> findContentsByBoard_IdOrderByIdAsc(@Param("id") Long id);
}
