package com.autowise.demo.service;

import com.autowise.demo.dto.AdminRegisterDto;
import com.autowise.demo.dto.LoginDto;
import com.autowise.demo.dto.UserDto;
import com.autowise.demo.mapper.UserMapper;
import com.autowise.demo.model.User;
import com.autowise.demo.repository.UserRepository;
import com.autowise.demo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // ✅ normal user register
    public void register(UserDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .hashPassword(passwordEncoder.encode(request.getPassword()))
                .nrc(request.getNrc())
                .phone(request.getPhone())
                .address(request.getAddress())
                .role("USER")
                .build();

        userRepository.save(user);
    }

    // ✅ admin register (ADMIN only should call this)
    public void registerAdmin(AdminRegisterDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User admin = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .hashPassword(passwordEncoder.encode(request.getPassword()))
                .role("ADMIN")
                .build();

        userRepository.save(admin);
    }

    public UserDto login(LoginDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getHashPassword())
                .authorities("ROLE_" + user.getRole())
                .build();

        String token = jwtService.generateToken(userDetails);

        UserDto userDto = userMapper.toDto(user);

        // ✅ Option A: set role manually
        userDto.setRole(user.getRole());

        // ✅ set token
        userDto.setToken(token);

        // ✅ do not return password
        userDto.setPassword(null);

        return userDto;
    }

}
