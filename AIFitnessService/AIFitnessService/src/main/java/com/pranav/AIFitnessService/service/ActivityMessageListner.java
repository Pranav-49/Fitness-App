package com.pranav.AIFitnessService.service;

import com.pranav.AIFitnessService.model.Activity;
import com.pranav.AIFitnessService.model.Recommendation;
import com.pranav.AIFitnessService.repository.RecommendationRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListner {

    private final ActivityAIService activityAIService;
    private final RecommendationRepository repository;

    @KafkaListener(topics = "activity-events" , groupId = "activity-processor-group")
    public void processActivity(Activity activity)
    {
        log.info("Received Activity for Processing : {}",activity.getUserId());
        Recommendation recommendation = activityAIService.generateRecommendation(activity);
        repository.save(recommendation);
    }
}
