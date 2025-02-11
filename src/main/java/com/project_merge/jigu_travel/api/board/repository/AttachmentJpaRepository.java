package com.project_merge.jigu_travel.api.board.repository;

import com.project_merge.jigu_travel.api.board.entity.Attachment;
import com.project_merge.jigu_travel.api.board.entity.Board;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachmentJpaRepository extends JpaRepository<Attachment, Long> {
    Optional<Attachment> findByFileName(String fileName);

    @Transactional
    void deleteByBoardAndFileNameIn(Board board, List<String> fileNames);
}
