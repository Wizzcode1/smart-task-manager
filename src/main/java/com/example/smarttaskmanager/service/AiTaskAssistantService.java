package com.example.smarttaskmanager.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiTaskAssistantService {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.endpoint}")
    private String endpoint;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public String suggestPriority(String description) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            String requestBody = """
                    {
                      "model": "gpt-3.5-turbo",
                      "messages": [
                        {
                          "role": "system",
                          "content": "You are an AI assistant that classifies task descriptions into one of three priorities: LOW, MEDIUM, or HIGH. Respond with only one word: the priority."
                        },
                        {
                          "role": "user",
                          "content": "Classify the priority of the task: %s"
                        }
                      ],
                      "temperature": 0.2
                    }
                    """.formatted(description);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    endpoint, HttpMethod.POST, entity, String.class
            );

            JsonNode root = objectMapper.readTree(response.getBody());
            String priority = root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText()
                    .trim()
                    .toUpperCase();

            log.info("Suggested priority: {}", priority);
            return priority;

        }
        catch (Exception e) {
            log.error("AI priority suggestion failed", e);
            return "MEDIUM";
        }
    }
}
