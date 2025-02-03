package com.project_merge.jigu_travel.api.board.repository;

import com.project_merge.jigu_travel.api.board.entity.Attachment;
import com.project_merge.jigu_travel.api.board.entity.Board;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentJpaRepository extends JpaRepository<Attachment, Long> {
//    List<Attachment> findByBoard(Board board); // 특정 게시글의 첨부파일 조회

    @Transactional
    void deleteByBoardAndFileNameIn(Board board, List<String> fileNames);
}
