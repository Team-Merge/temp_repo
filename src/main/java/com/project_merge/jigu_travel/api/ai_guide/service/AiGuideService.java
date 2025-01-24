package com.project_merge.jigu_travel.api.ai_guide.service;

import com.project_merge.jigu_travel.api.ai_guide.dto.AiGuideAudioResponse;
import com.project_merge.jigu_travel.api.ai_guide.dto.AiGuideTextResponse;
import com.project_merge.jigu_travel.api.ai_guide.entity.ConversationHistory;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AiGuideService {

    //== 대화 내역 로그 저장==//
    public void saveConversationHistory(ConversationHistory conversationHistory) ;
    public AiGuideTextResponse handleTextQuestion(String userQuestion, HttpSession session);
    public AiGuideAudioResponse handleAudioAndQuestion(MultipartFile audioFile , HttpSession session);
}
