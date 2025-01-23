package com.project_merge.jigu_travel.api.ai_guide.service;


import com.project_merge.jigu_travel.api.ai_guide.entity.ConversationHistory;
import com.project_merge.jigu_travel.api.ai_guide.repository.ConversationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationHistoryServiceImpl implements ConversationHistoryService{

    private final ConversationHistoryRepository conversationHistoryRepository;

    @Override
    public void saveConversationHistory(ConversationHistory conversationHistory) {
         conversationHistoryRepository.save(conversationHistory);
    }
}
