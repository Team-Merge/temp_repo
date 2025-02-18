package com.project_merge.jigu_travel.api.board.comment.repository;

import com.project_merge.jigu_travel.api.board.comment.entity.Comment;
import com.project_merge.jigu_travel.api.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardOrderByCreatedAtAsc(Board board);
}
