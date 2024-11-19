package com.gal.bc_inside.domain.board.repository;

import com.gal.bc_inside.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findBoardById(Long id);
}
