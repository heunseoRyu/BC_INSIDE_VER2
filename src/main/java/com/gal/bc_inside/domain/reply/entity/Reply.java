package com.gal.bc_inside.domain.reply.entity;

import com.gal.bc_inside.domain.board.entity.Board;
import com.gal.bc_inside.domain.comment.entity.Comment;
import com.gal.bc_inside.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_reply")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_member_id", nullable = false)
    private Member member;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_comment_id", nullable = false)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_board_id", nullable = false)
    private Board board;
}
