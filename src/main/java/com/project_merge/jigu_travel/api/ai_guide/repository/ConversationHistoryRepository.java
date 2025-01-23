package com.project_merge.jigu_travel.api.ai_guide.repository;

import com.project_merge.jigu_travel.api.ai_guide.entity.ConversationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationHistoryRepository extends JpaRepository<ConversationHistory, Integer> {
}
