package com.project_merge.jigu_travel.api.ai_guide.controller;

import com.project_merge.jigu_travel.api.ai_guide.dto.AudioResponse;
import com.project_merge.jigu_travel.api.ai_guide.dto.TextResponse;
import com.project_merge.jigu_travel.api.ai_guide.entity.ConversationHistory;
import com.project_merge.jigu_travel.api.ai_guide.fast.FastApiClient;
import com.project_merge.jigu_travel.api.ai_guide.service.AiGuideService;
import com.project_merge.jigu_travel.api.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController  // REST API 요청을 처리하는 컨트롤러
@RequiredArgsConstructor
@RequestMapping("/api/ai-guide")
public class AiGuideRestController {
    private final FastApiClient fastApiClient; //fast api
    private final UserService userService; //user_id 불러오기
    private final AiGuideService aiGuideService;

    // 음성 녹음 파일 처리
    @PostMapping("/upload-audio")
    public AudioResponse uploadAudio(@RequestParam("audio") MultipartFile audioFile, HttpSession session) {
        if (audioFile.isEmpty()) {
            System.out.println("파일 비어있음");
        }

        System.out.println("audio_file name: " + audioFile.getOriginalFilename());
        // 여기서 실제 파일 처리 로직 추가

        return aiGuideService.handleAudioAndQuestion(audioFile,session);
    }

    // 텍스트 질문 처리
    @PostMapping("/upload-text")
    public TextResponse uploadText(@RequestBody Map<String, String> requestBody, HttpSession session) {
        try {
            String userQuestion = requestBody.get("user_question"); // JSON에서 'user_question' 값을 가져옴
            return aiGuideService.handleTextQuestion(userQuestion, session);
        } catch (Exception e) {
            e.printStackTrace();
            return new TextResponse("API 요청 실패", null);
        }
    }

    // 대화 기록 처리
    @GetMapping("/get-chat-history")
    public List<ConversationHistory> getChatHistory(@RequestParam int offset, @RequestParam int limit,HttpSession session) {
        System.out.println("컨트롤러 확인");
        return aiGuideService.handleChatHistory(offset,limit,session);
    }
}