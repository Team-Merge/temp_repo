package com.project_merge.jigu_travel.board.repository;

import com.project_merge.jigu_travel.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardJpaRepository extends JpaRepository<Board, Long> {

}
