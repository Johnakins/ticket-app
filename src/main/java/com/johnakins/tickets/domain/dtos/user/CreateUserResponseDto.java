package com.johnakins.tickets.domain.dtos.user;

import com.johnakins.tickets.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponseDto {
    private UUID id;
    //private String name;
    private String email;
    private Role role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}