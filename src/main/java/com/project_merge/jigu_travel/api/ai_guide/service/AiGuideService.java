package com.project_merge.jigu_travel.api.ai_guide.service;

import com.project_merge.jigu_travel.api.ai_guide.dto.AudioResponse;
import com.project_merge.jigu_travel.api.ai_guide.dto.TextResponse;
import com.project_merge.jigu_travel.api.ai_guide.dto.UserInputRequest;
import com.project_merge.jigu_travel.api.ai_guide.entity.ConversationHistory;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AiGuideService {

    // 텍스트 질문 처리 메서드
    TextResponse handleTextQuestion(String userQuestion, HttpSession session);

    // 음성 질문 처리 메서드
    AudioResponse handleAudioAndQuestion(MultipartFile audioFile, HttpSession session);
}
