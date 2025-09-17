package com.pranav.AIFitnessService.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Data
public class GeminiService {
    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";
    @Value("${gemini.api.key}")
    private String getGeminiApiKey = "AIzaSyBUWL4MOQtZTCfwiiHPes3rtP7G8X_jmVc";

    public GeminiService(WebClient.Builder webClientBuilder)
    {
        this.webClient = webClientBuilder.build();
    }

    public String getRecommendation(String details) {
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", details)
                        })
                }
        );
        String responce = webClient.post()
                .uri(geminiApiUrl)
                .header("Content-Type","application/json")
                .header("X-gogg-api-key",getGeminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return responce;
    }
}
