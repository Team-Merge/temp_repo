package com.project_merge.jigu_travel.api.ai_guide.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_merge.jigu_travel.api.ai_guide.dto.AiGuideAudioResponse;
import com.project_merge.jigu_travel.api.ai_guide.dto.AiGuideTextResponse;
import com.project_merge.jigu_travel.api.ai_guide.dto.UserInputRequestDto;
import com.project_merge.jigu_travel.api.ai_guide.entity.ConversationHistory;
import com.project_merge.jigu_travel.api.ai_guide.fast.FastApiClient;
import com.project_merge.jigu_travel.api.ai_guide.repository.ConversationHistoryRepository;
import com.project_merge.jigu_travel.api.ai_guide.utils.MultipartInputStreamFileResource;
import com.project_merge.jigu_travel.api.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;


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
    private final RestTemplate restTemplate;

    @Value("${ai-guide.api.host}") // application.properties에서 FastAPI 서버 URL을 읽어옴
    private String fastApiUrl;

    @Override
    public void saveConversationHistory(ConversationHistory conversationHistory) {
         conversationHistoryRepository.save(conversationHistory);
    }

    //텍스트 질문
    @Override
    public AiGuideTextResponse handleTextQuestion(String userQuestion, HttpSession session) {
        try {
            // 세션에서 대화 기록 가져오기 (없으면 빈 대화 기록 생성)
            UserInputRequestDto.ConversationHistory conversationHistory =
                    (UserInputRequestDto.ConversationHistory) session.getAttribute("conversation_history");

            if (conversationHistory == null) {
                conversationHistory = new UserInputRequestDto.ConversationHistory();
                conversationHistory.setHistory(new ArrayList<>());
            }

            // UserInput 생성 (이전 대화 기록을 포함)
            UserInputRequestDto userInput = new UserInputRequestDto();
            userInput.setUser_question(userQuestion);
            userInput.setUser_category(Arrays.asList("맛집", "힐링")); // 수정 예정
            userInput.setLatitude(37.508373); // 수정 예정
            userInput.setLongitude(127.103565); // 수정 예정
            userInput.setConversation_history(conversationHistory);

            // FastAPI에 요청 보내기
            AiGuideTextResponse response = fastApiClient.getAiGuideText(userInput);

            // 새로운 대화 항목 추가
            UserInputRequestDto.ConversationHistory.ConversationHistoryItem newItem =
                    new UserInputRequestDto.ConversationHistory.ConversationHistoryItem();
            newItem.setUser_question(response.getConversation_history().getHistory().get(0).getUser_question());
            newItem.setAssistant_response(response.getConversation_history().getHistory().get(0).getAssistant_response());

            // 대화 기록을 1개만 저장
            conversationHistory.getHistory().clear(); // 기존 기록 삭제
            conversationHistory.getHistory().add(newItem);

            // 대화 기록을 세션에 저장
            session.setAttribute("conversation_history", conversationHistory);

            // DB에 저장
            UUID user_id = userService.getCurrentUserUUID();
            ConversationHistory history = new ConversationHistory();
            history.setUser_id(user_id);
            history.setConversation_question(response.getConversation_history().getHistory().get(0).getUser_question());
            history.setConversation_answer(response.getConversation_history().getHistory().get(0).getAssistant_response());
            history.setConversation_latitude(userInput.getLatitude());
            history.setConversation_longitude(userInput.getLongitude());
            history.setConversation_datetime(LocalDateTime.now());

            saveConversationHistory(history);
            System.out.println("DB 저장 완료");

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("API 요청 실패");
        }
    }

    @Override
    public AiGuideAudioResponse handleAudioAndQuestion( MultipartFile audioFile , HttpSession session) {
        try {
            // 세션에서 대화 기록 가져오기 (없으면 빈 대화 기록 생성)
            UserInputRequestDto.ConversationHistory conversationHistory =
                    (UserInputRequestDto.ConversationHistory) session.getAttribute("conversation_history");

            if (conversationHistory == null) {
                conversationHistory = new UserInputRequestDto.ConversationHistory();
                conversationHistory.setHistory(new ArrayList<>());
            }

            // 로그 출력
            System.out.println("FastAPI 요청 시작...");
            System.out.println("audioFile name: " + audioFile.getOriginalFilename());

            UserInputRequestDto userInput = new UserInputRequestDto();
            userInput.setUser_question(" ");
            userInput.setUser_category(Arrays.asList("맛집", "힐링")); // 수정 예정
            userInput.setLatitude(37.508373); // 수정 예정
            userInput.setLongitude(127.103565); // 수정 예정
            userInput.setConversation_history(conversationHistory);

            // Jackson을 이용해 UserInputRequestDto 객체를 JSON으로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String userInputJson = objectMapper.writeValueAsString(userInput);
//            String userInputJson =
//                    "{\"user_question\":\" \",\"user_category\":[\"맛집\",\"힐링\"],\"latitude\":37.508373,\"longitude\":127.103565,\"conversation_history\":{\"history\":[]}}";
            System.out.println(userInputJson);


            // FastAPI 서버로 음성 파일과 질문을 전송
            AiGuideAudioResponse response = fastApiClient.getAiGuideAudio(audioFile, userInputJson);

            // 응답 로그 출력
            System.out.println("Response Status: " + response);

            // 새로운 대화 항목 추가
            UserInputRequestDto.ConversationHistory.ConversationHistoryItem newItem =
                    new UserInputRequestDto.ConversationHistory.ConversationHistoryItem();
            newItem.setUser_question(response.getConversation_history().getHistory().get(0).getUser_question());
            newItem.setAssistant_response(response.getConversation_history().getHistory().get(0).getAssistant_response());

            // 대화 기록을 1개만 저장
            conversationHistory.getHistory().clear(); // 기존 기록 삭제
            conversationHistory.getHistory().add(newItem);

            // 대화 기록을 세션에 저장
            session.setAttribute("conversation_history", conversationHistory);

            // DB에 저장
            UUID user_id = userService.getCurrentUserUUID();
            ConversationHistory history = new ConversationHistory();
            history.setUser_id(user_id);
            history.setConversation_question(response.getConversation_history().getHistory().get(0).getUser_question());
            history.setConversation_answer(response.getConversation_history().getHistory().get(0).getAssistant_response());
            history.setConversation_latitude(userInput.getLatitude());
            history.setConversation_longitude(userInput.getLongitude());
            history.setConversation_datetime(LocalDateTime.now());

            saveConversationHistory(history);
            System.out.println("DB 저장 완료");


            return response; // FastAPI 응답 그대로 반환
        } catch (Exception e) {
            System.out.println("Error during API call: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("API 요청 실패");
        }
    }
}
