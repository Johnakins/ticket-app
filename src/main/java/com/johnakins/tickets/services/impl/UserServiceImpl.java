package com.johnakins.tickets.services.impl;

import com.johnakins.tickets.domain.dtos.createUser.CreateUserRequestDto;
import com.johnakins.tickets.domain.dtos.createUser.CreateUserResponseDto;
import com.johnakins.tickets.domain.dtos.createUser.LoginDto;
import com.johnakins.tickets.domain.entity.User;
import com.johnakins.tickets.mappers.UserMapper;
import com.johnakins.tickets.repositories.UserRepository;
import com.johnakins.tickets.services.UserService;
import com.johnakins.tickets.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public CreateUserResponseDto createUser(CreateUserRequestDto request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(request.getRole());

        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public String loginUser(LoginDto request){
        String username = request.getName();
        String password = request.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = userRepository.findByName(username).orElseThrow();
        UUID userId = user.getId();
        //List<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        String role = user.getRole().toString();
        return jwtUtil.generateToken(userId, role);
    }

    @Override
    public CreateUserResponseDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public List<CreateUserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }



    @Override
    public CreateUserResponseDto updateUser(UUID id, CreateUserRequestDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        // Encode new password only if provided
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

}
