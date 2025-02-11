package com.project_merge.jigu_travel.api.ai_guide.service;

import com.project_merge.jigu_travel.api.ai_guide.dto.AudioResponse;
import com.project_merge.jigu_travel.api.ai_guide.dto.TextResponse;
import com.project_merge.jigu_travel.api.ai_guide.entity.ConversationHistory;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AiGuideService {

    // 텍스트 질문 처리 메서드
    TextResponse handleTextQuestion(String userQuestion, HttpSession session);

    // 음성 질문 처리 메서드
    AudioResponse handleAudioAndQuestion(MultipartFile audioFile, HttpSession session);

    //대화 기록 처리 함수
    List<ConversationHistory> handleChatHistory(int offset, int limit);
}
