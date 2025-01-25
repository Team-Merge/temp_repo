package com.project_merge.jigu_travel.api.ai_guide.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInputRequest {
    private String user_question;
    private List<String> user_category;
    private double latitude;
    private double longitude;
    private ConversationHistory conversation_history = new ConversationHistory();  // 빈 리스트로 초기화

    @Data
    public static class ConversationHistory {
        private List<ConversationHistoryItem> history = new ArrayList<>();  // 빈 리스트로 초기화

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
