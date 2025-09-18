package com.pranav.AIFitnessService.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranav.AIFitnessService.model.Activity;
import com.pranav.AIFitnessService.model.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {
    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity)
    {
        String prompt = createPromptForActivity(activity);
        String aiResponce = geminiService.getRecommendation(prompt);
        log.info("RESPONCE FROM AI : {}",aiResponce);
        return processAiResponse(activity,aiResponce);
    }

    private Recommendation processAiResponse(Activity activity, String aiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);

            JsonNode candidatesNode = rootNode.path("candidates");
            if (!candidatesNode.isArray() || candidatesNode.isEmpty()) {
                log.warn("No candidates found in AI response.");
                return null;
            }

            JsonNode partsNode = candidatesNode.get(0).path("content").get("parts");
            if (!partsNode.isArray() || partsNode.isEmpty()) {
                log.warn("No parts found in AI response.");
                return null;
            }

            String text = partsNode.get(0).path("text").asText()
                    .replaceAll("```json\\n","")
                    .replaceAll("\\n```","")
                    .trim();

            log.info("RESPONCE FROM CLEANEDAI : {}", text);

        } catch (Exception e) {
            log.error("Error processing AI response", e);
            return null;
        }

        return null;
    }


    private String createPromptForActivity(Activity activity) {
        return String.format("""
        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
        {
          "analysis": {
            "overall": "Overall analysis here",
            "pace": "Pace analysis here",
            "heartRate": "Heart rate analysis here",
            "caloriesBurned": "Calories analysis here"
          },
          "improvements": [
            {
              "area": "Area name",
              "recommendation": "Detailed recommendation"
            }
          ],
          "suggestions": [
            {
              "workout": "Workout name",
              "description": "Detailed workout description"
            }
          ],
          "safety": [
            "Safety point 1",
            "Safety point 2"
          ]
        }

        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        Additional Metrics: %s
        
        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
        Ensure the response follows the EXACT JSON format shown above.
        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurn(),
                activity.getAdditionalMatrics()
        );
    }
}
