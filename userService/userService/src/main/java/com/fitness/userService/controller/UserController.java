package com.fitness.userService.controller;

import com.fitness.userService.dto.ResgisterRequest;
import com.fitness.userService.dto.UserResponse;
import com.fitness.userService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody ResgisterRequest request)
    {
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId)
    {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @GetMapping("/{id}/validate")
    public ResponseEntity<Boolean> validateUser(@PathVariable String id)
    {
        return ResponseEntity.ok(userService.existByUserId(id));
    }
}
