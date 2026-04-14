package com.taskmaster.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class ClaudeAiService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    private final WebClient webClient;

    public ClaudeAiService() {
        this.webClient = WebClient.builder().build();
    }

    public String generateTaskDescription(String taskTitle) {
        try {
            Map<String, Object> requestBody = Map.of(
//                "model", "llama3-8b-8192",
            	"model", "llama-3.3-70b-versatile",
                "max_tokens", 300,
                "messages", List.of(
                    Map.of(
                        "role", "user",
                        "content", "Generate a concise, professional task description (2-3 sentences) for a task titled: \""
                            + taskTitle + "\". Include what needs to be done, why it matters, and key steps. Keep it practical and actionable. Only return the description, no extra text."
                    )
                )
            );

            Map response = webClient.post()
                    .uri(apiUrl)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null && response.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return (String) message.get("content");
                }
            }

            return "Unable to generate description. Please write your own.";

        } catch (Exception e) {
            return "AI generation failed: " + e.getMessage() + ". Please write your own description.";
        }
    }
}