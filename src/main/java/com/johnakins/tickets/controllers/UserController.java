package com.johnakins.tickets.controllers;

import com.johnakins.tickets.domain.dtos.createUser.CreateUserRequestDto;
import com.johnakins.tickets.domain.dtos.createUser.CreateUserResponseDto;
import com.johnakins.tickets.domain.dtos.createUser.LoginDto;
import com.johnakins.tickets.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<CreateUserResponseDto> createUser(
            @Valid @RequestBody CreateUserRequestDto request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto request) {
        String token = userService.loginUser(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateUserResponseDto> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<CreateUserResponseDto>> listUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreateUserResponseDto> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody CreateUserRequestDto request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}