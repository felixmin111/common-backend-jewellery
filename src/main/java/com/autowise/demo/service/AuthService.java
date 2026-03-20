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

import com.autowise.demo.model.PasswordResetToken;
import com.autowise.demo.repository.PasswordResetTokenRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailSenderService emailSenderService;

    public void register(UserDto request) {

        // 🔥 CHECK GMAIL
        if (!request.getEmail().endsWith("@gmail.com")) {
            throw new RuntimeException("Only Gmail is allowed");
        }

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

    public void forgotPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setEmail(email);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));

        tokenRepository.save(resetToken);

        String link = "http://localhost:5173/reset-password?token=" + token;

        emailSenderService.sendEmail(
                email,
                "Reset Password",
                "Dear user,\n\n" +
                        "Click the link below to reset your password:\n" +
                        link + "\n\n" +
                        "This link will expire in 15 minutes.\n\n" +
                        "If you did not request this, please ignore this email."
        );
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {

        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = userRepository.findByEmail(resetToken.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 CRITICAL LINE
        user.setHashPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        tokenRepository.delete(resetToken);

        System.out.println(user.getHashPassword());
        System.out.println("Password updated for: " + user.getEmail());
    }

}
