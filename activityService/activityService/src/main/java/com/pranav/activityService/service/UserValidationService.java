package com.pranav.activityService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class UserValidationService {
    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId)
    {
        try {
                Boolean isValid = userServiceWebClient.get()
                        .uri("http://user-service/api/users/{id}/validate", userId)
                        .retrieve()
                        .bodyToMono(Boolean.class)
                        .block();

                return Boolean.TRUE.equals(isValid);
        }catch (WebClientResponseException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
