package com.pranav.AIFitnessService.service;

import com.pranav.AIFitnessService.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListner {

    private final ActivityAIService activityAIService;

    @KafkaListener(topics = "activity-events" , groupId = "activity-processor-group")
    public void processActivity(Activity activity)
    {
        log.info("Received Activity for Processing : {}",activity.getUserId());
        activityAIService.generateRecommendation(activity);
    }
}
