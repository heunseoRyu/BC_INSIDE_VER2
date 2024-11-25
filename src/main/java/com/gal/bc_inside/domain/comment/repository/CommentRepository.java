package com.gal.bc_inside.domain.comment.repository;

import com.gal.bc_inside.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoard_IdOrderByIdDesc(Long id);

    @Query("select c.content from Comment c where c.board.id = :id order by c.id desc")
    List<String> findContentsByBoard_IdOrderByIdAsc(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value="delete from tb_comment where fk_board_id = :boardId",nativeQuery = true)
    void deleteByBoard_Id(@Param("boardId") Long boardId);
}
