package com.pranav.activityService.service;

import com.pranav.activityService.dto.ActivityRequest;
import com.pranav.activityService.dto.ActivityResponce;
import com.pranav.activityService.model.Activity;
import com.pranav.activityService.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public ActivityResponce trackActivity(ActivityRequest activityRequest) {
        Activity activity = Activity.builder()
                .userId(activityRequest.getUserId())
                .type(activityRequest.getType())
                .duration(activityRequest.getDuration())
                .caloriesBurn(activityRequest.getCaloriesBurn())
                .startTime(activityRequest.getStartTime())
                .additionalMatrics(activityRequest.getAdditionalMatrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);
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
