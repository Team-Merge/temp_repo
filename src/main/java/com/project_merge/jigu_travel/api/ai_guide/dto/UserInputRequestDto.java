package com.project_merge.jigu_travel.api.ai_guide.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInputRequestDto {
    private String user_question;
    private List<String> user_category;
    private double latitude;
    private double longitude;
    private ConversationHistory conversation_history;  // ConversationHistory 객체

    @Data
    public static class ConversationHistory {
        private List<ConversationHistoryItem> history;  // History 리스트

        @Data
        public static class ConversationHistoryItem {
            private String user_question;  // 사용자 질문
            private QuestionSummary question_summary;  // 질문 요약
            private String assistant_response;  // AI 응답
        }

        @Data
        public static class QuestionSummary {
            private String type;  // 질문의 유형 (예: 'other')
            private List<String> keyword;  // 질문에 포함된 키워드들
        }
    }
}