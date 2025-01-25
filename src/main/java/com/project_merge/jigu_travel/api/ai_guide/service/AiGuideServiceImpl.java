package com.project_merge.jigu_travel.api.ai_guide.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_merge.jigu_travel.api.ai_guide.dto.AudioResponse;
import com.project_merge.jigu_travel.api.ai_guide.dto.TextResponse;
import com.project_merge.jigu_travel.api.ai_guide.dto.UserInputRequest;
import com.project_merge.jigu_travel.api.ai_guide.entity.ConversationHistory;
import com.project_merge.jigu_travel.api.ai_guide.fast.FastApiClient;
import com.project_merge.jigu_travel.api.ai_guide.repository.ConversationHistoryRepository;
import com.project_merge.jigu_travel.api.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AiGuideServiceImpl implements AiGuideService {

    private final ConversationHistoryRepository conversationHistoryRepository;
    private final UserService userService;
    private final FastApiClient fastApiClient;


    // 텍스트 질문 처리
    @Override
    public TextResponse handleTextQuestion(String userQuestion, HttpSession session) {
        try {
            // 세션에서 대화 기록 가져오기
            UserInputRequest.ConversationHistory conversationHistory = getConversationHistoryFromSession(session);

            // UserInput 생성
            UserInputRequest userInput = createUserInput(userQuestion, conversationHistory);

            // FastAPI에 요청 보내기
            TextResponse response = fastApiClient.getAiGuideText(userInput);

            // 새로운 대화 항목 추가
            UserInputRequest.ConversationHistory.ConversationHistoryItem newItem = createConversationHistoryItem(response);

            // 세션과 DB에 대화 기록 저장
            saveConversationHistoryToSession(session, conversationHistory);
            saveConversationHistoryToDb(newItem, userInput, userService.getCurrentUserUUID());

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("API 요청 실패");
        }
    }

    // 음성 질문 처리
    @Override
    public AudioResponse handleAudioAndQuestion(MultipartFile audioFile, HttpSession session) {
        try {
            // 세션에서 대화 기록 가져오기
            UserInputRequest.ConversationHistory conversationHistory = getConversationHistoryFromSession(session);

            // 로그 출력
            System.out.println("FastAPI 요청 시작...");
            System.out.println("audioFile name: " + audioFile.getOriginalFilename());

            // UserInput 생성
            UserInputRequest userInput = createUserInput(" ", conversationHistory);

            // FastAPI 서버로 음성 파일과 질문을 전송
            AudioResponse response = fastApiClient.getAiGuideAudio(audioFile, convertUserInputToJson(userInput));

            // 새로운 대화 항목 추가
            UserInputRequest.ConversationHistory.ConversationHistoryItem newItem = createConversationHistoryItem(response);

            // 세션과 DB에 대화 기록 저장
            saveConversationHistoryToSession(session, conversationHistory);
            saveConversationHistoryToDb(newItem, userInput, userService.getCurrentUserUUID());

            return response; // FastAPI 응답 그대로 반환
        } catch (Exception e) {
            System.out.println("Error during API call: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("API 요청 실패");
        }
    }

    // 대화 기록 저장 함수
    private void saveConversationHistoryToSession(HttpSession session, UserInputRequest.ConversationHistory conversationHistory) {
        session.setAttribute("conversation_history", conversationHistory);
    }

    // 대화 기록 DB에 저장
    private void saveConversationHistoryToDb(UserInputRequest.ConversationHistory.ConversationHistoryItem item, UserInputRequest userInput, UUID userId) {
        ConversationHistory history = new ConversationHistory();
        history.setUser_id(userId);
        history.setConversation_question(item.getUser_question());
        history.setConversation_answer(item.getAssistant_response());
        history.setConversation_latitude(userInput.getLatitude());
        history.setConversation_longitude(userInput.getLongitude());
        history.setConversation_datetime(LocalDateTime.now());

        conversationHistoryRepository.save(history);
        System.out.println("DB 저장 완료");
    }


    // 대화 기록 세션에서 가져오기
    private UserInputRequest.ConversationHistory getConversationHistoryFromSession(HttpSession session) {
        UserInputRequest.ConversationHistory conversationHistory = (UserInputRequest.ConversationHistory) session.getAttribute("conversation_history");
        if (conversationHistory == null) {
            conversationHistory = new UserInputRequest.ConversationHistory();
            conversationHistory.setHistory(new ArrayList<>());
        }
        return conversationHistory;
    }

    // UserInput 생성
    private UserInputRequest createUserInput(String userQuestion, UserInputRequest.ConversationHistory conversationHistory) {
        UserInputRequest userInput = new UserInputRequest();
        userInput.setUser_question(userQuestion);
        userInput.setUser_category(Arrays.asList("맛집", "힐링"));
        userInput.setLatitude(37.508373); // 수정 예정
        userInput.setLongitude(127.103565); // 수정 예정
        userInput.setConversation_history(conversationHistory);
        return userInput;
    }

    // UserInput 객체를 JSON으로 변환
    private String convertUserInputToJson(UserInputRequest userInput) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(userInput);
    }

    // 대화 항목 생성
    private UserInputRequest.ConversationHistory.ConversationHistoryItem createConversationHistoryItem(Object response) {
        UserInputRequest.ConversationHistory.ConversationHistoryItem newItem = new UserInputRequest.ConversationHistory.ConversationHistoryItem();
        if (response instanceof TextResponse) {
            newItem.setUser_question(((TextResponse) response).getConversation_history().getHistory().get(0).getUser_question());
            newItem.setAssistant_response(((TextResponse) response).getConversation_history().getHistory().get(0).getAssistant_response());
        } else if (response instanceof AudioResponse) {
            newItem.setUser_question(((AudioResponse) response).getConversation_history().getHistory().get(0).getUser_question());
            newItem.setAssistant_response(((AudioResponse) response).getConversation_history().getHistory().get(0).getAssistant_response());
        }
        return newItem;
    }
}
