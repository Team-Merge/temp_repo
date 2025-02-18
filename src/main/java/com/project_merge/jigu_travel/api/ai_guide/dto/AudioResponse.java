package com.project_merge.jigu_travel.api.ai_guide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudioResponse {
    private ConversationHistory conversation_history;  // ConversationHistory 객체
    private String file_url; //음성 파일

    public AudioResponse(String message, Object o) { //요청실패시
        this.conversation_history = new ConversationHistory();
    }

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