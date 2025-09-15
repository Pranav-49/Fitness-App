package com.pranav.activityService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Service
@RequiredArgsConstructor
public class UserValidationService {
    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId)
    {
        try {
                Boolean isValid = userServiceWebClient.get()
                        .uri("/api/users/{userId}/validate", userId)
                        .retrieve()
                        .bodyToMono(Boolean.class)
                        .block();

                return Boolean.TRUE.equals(isValid);
        }catch (WebClientException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
