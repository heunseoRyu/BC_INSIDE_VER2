package com.gal.bc_inside.domain.ai.service;

import com.gal.bc_inside.domain.ai.dto.ChatGPTRequest;
import com.gal.bc_inside.domain.ai.dto.ChatGPTResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatGptService {

    @Value("${openai.model}")
    private String model; // OpenAI 모델 이름

    @Value("${openai.api.url}")
    private String apiUrl; // OpenAI API URL

    private final RestTemplate restTemplate; // RestTemplate 빈 주입

    // 사용자 질문에 대한 응답을 반환하는 메서드
    public String getResponse(String prompt) {
        // 파일에서 일치하는 내용이 없으면 ChatGPT API 호출
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse chatGPTResponse = restTemplate.postForObject(apiUrl, request, ChatGPTResponse.class);
        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }

}
