package com.fitness.userService.service;

import com.fitness.userService.dto.ResgisterRequest;
import com.fitness.userService.dto.UserResponse;
import com.fitness.userService.modules.User;
import com.fitness.userService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserResponse register(ResgisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail()))
            throw  new RuntimeException("Email Is Already Exist");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());

        User savedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();

        userResponse.setId(savedUser.getId());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());

        return userResponse;
    }

    public UserResponse getUserProfile(String userId) {

        User userProfile = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userProfile.getId());
        userResponse.setUpdatedAt(userProfile.getUpdatedAt());
        userResponse.setEmail(userProfile.getEmail());
        userResponse.setPassword(userProfile.getPassword());
        userResponse.setFirstName(userProfile.getFirstName());
        userResponse.setLastName(userProfile.getLastName());
        userResponse.setCreatedAt(userProfile.getCreatedAt());
        return userResponse;
    }
}
