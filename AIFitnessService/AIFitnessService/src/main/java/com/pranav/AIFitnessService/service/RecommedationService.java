package com.pranav.AIFitnessService.service;

import com.pranav.AIFitnessService.model.Recommendation;
import com.pranav.AIFitnessService.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommedationService {

    private final RecommendationRepository repository;

    public List<Recommendation> getUserRecommendation(String userId) {
        return repository.findByUserId(userId);
    }

    public Recommendation getActivityRecommendation(String activityId) {
        return repository.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("No recommendation found for this activity"+activityId));
    }
}
