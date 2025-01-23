package com.project_merge.jigu_travel.api.ai_guide.service;

import com.project_merge.jigu_travel.api.ai_guide.entity.ConversationHistory;

public interface ConversationHistoryService {

    //== 대화 내역 로그 저장==//
    public void saveConversationHistory(ConversationHistory conversationHistory) ;

}
