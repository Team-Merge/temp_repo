package com.project_merge.jigu_travel.api.ai_guide.repository;

import com.project_merge.jigu_travel.api.ai_guide.entity.ConversationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConversationHistoryRepository extends JpaRepository<ConversationHistory, Integer> {

    //대화 기록 가져오는 쿼리
    Page<ConversationHistory> findByUserIdOrderByConversationDatetimeDesc(UUID userId, Pageable pageable);

}
