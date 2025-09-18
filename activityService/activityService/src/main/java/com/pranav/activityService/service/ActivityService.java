package com.pranav.activityService.service;

import com.pranav.activityService.dto.ActivityRequest;
import com.pranav.activityService.dto.ActivityResponce;
import com.pranav.activityService.model.Activity;
import com.pranav.activityService.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final KafkaTemplate<String,Activity> kafkaTemplate;
    private String topicName = "activity-events";

    public ActivityResponce trackActivity(ActivityRequest activityRequest) {

        boolean isValidUser = userValidationService.validateUser(activityRequest.getUserId());

        if(!isValidUser)
        {
            throw new RuntimeException("Invalid User"+activityRequest.getUserId());
        }

        Activity activity = Activity.builder()
                .userId(activityRequest.getUserId())
                .type(activityRequest.getType())
                .duration(activityRequest.getDuration())
                .caloriesBurn(activityRequest.getCaloriesBurn())
                .startTime(activityRequest.getStartTime())
                .additionalMatrics(activityRequest.getAdditionalMatrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        try {
            kafkaTemplate.send(topicName,savedActivity.getUserId(),activity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mapToResponce(savedActivity);
    }

    private ActivityResponce mapToResponce(Activity activity) {
        ActivityResponce responce = new ActivityResponce();
        responce.setId(activity.getId());
        responce.setType(activity.getType());
        responce.setDuration(activity.getDuration());
        responce.setCreatedAt(activity.getCreatedAt());
        responce.setCaloriesBurn(activity.getCaloriesBurn());
        responce.setAdditionalMatrics(activity.getAdditionalMatrics());
        responce.setStartTime(activity.getStartTime());
        responce.setUserId(activity.getUserId());
        responce.setUpdatedAt(activity.getUpdatedAt());

        return responce;
    }
}
