package com.johnakins.tickets.mappers;

import com.johnakins.tickets.domain.dtos.createUser.CreateUserResponseDto;
import com.johnakins.tickets.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public CreateUserResponseDto toDto(User user) {
        CreateUserResponseDto dto = new CreateUserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}
