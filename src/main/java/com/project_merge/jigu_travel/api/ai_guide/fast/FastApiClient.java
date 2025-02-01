package com.project_merge.jigu_travel.api.ai_guide.fast;

import com.project_merge.jigu_travel.api.ai_guide.dto.AudioResponse;
import com.project_merge.jigu_travel.api.ai_guide.dto.TextResponse;
import com.project_merge.jigu_travel.api.ai_guide.dto.UserInputRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * FastApiClient
 * 호출 파라미터 : 음성파일, user_input(위도,경도,사용자 카테고리,이전 대화)
 * 리턴값 : GPT 답변, 이전 대화
 */



@FeignClient(name = "fastApiClient", url = "${fast.api.host}") //perperites 등록
public interface FastApiClient {


    @PostMapping(value = "/user_question_voice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)  // add consumes to specify multipart
    AudioResponse getAiGuideAudio(
            @RequestPart("audio_file") MultipartFile file,
            @RequestPart("user_input") String userInput);

    @PostMapping("/user_question_text") //음성파일 제외 텍스트로 테스트
    TextResponse getAiGuideText(@RequestBody UserInputRequest userInputRequest);
}
