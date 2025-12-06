package com.johnakins.tickets.services;

import com.johnakins.tickets.domain.dtos.createUser.CreateUserRequestDto;
import com.johnakins.tickets.domain.dtos.createUser.CreateUserResponseDto;
import com.johnakins.tickets.domain.dtos.createUser.LoginDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    CreateUserResponseDto createUser(CreateUserRequestDto request);

    String loginUser(LoginDto request);

    CreateUserResponseDto getUserById(UUID id);

    List<CreateUserResponseDto> getAllUsers();

    CreateUserResponseDto updateUser(UUID id, CreateUserRequestDto request);

    void deleteUser(UUID id);

}
