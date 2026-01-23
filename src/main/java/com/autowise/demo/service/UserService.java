package com.autowise.demo.service;

import com.autowise.demo.dto.UserDto;
import com.autowise.demo.mapper.UserMapper;
import com.autowise.demo.model.User;
import com.autowise.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        return userMapper.toDto(user);
    }

    public UserDto create(UserDto request) {
        // email must be unique
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }
        if (request.getPhone() != null && !request.getPhone().isBlank()
                && userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone already exists: " + request.getPhone());
        }
        if (request.getNrc() != null && !request.getNrc().isBlank()
                && userRepository.existsByNrc(request.getNrc().trim())) {
            throw new RuntimeException("NRC already exists: " + request.getNrc());
        }

        User user = userMapper.toEntity(request);

        // IMPORTANT: your entity uses hashPassword, dto uses password
        user.setHashPassword(passwordEncoder.encode(request.getPassword()));

        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public UserDto update(Long id, UserDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        // if email is being changed, prevent duplicates
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }
        if (request.getPhone() != null && !request.getPhone().isBlank()
                && userRepository.existsByPhoneAndIdNot(request.getPhone(), id)) {
            throw new RuntimeException("Phone already exists: " + request.getPhone());
        }
        String incomingNrc = request.getNrc() == null ? null : request.getNrc().trim();
        if (incomingNrc != null && !incomingNrc.isBlank()) {
            userRepository.findByNrc(incomingNrc).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new RuntimeException("NRC already exists: " + incomingNrc);
                }
            });
        }

        userMapper.updateEntityFromDto(request, user);

        // if password provided -> update hash
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setHashPassword(passwordEncoder.encode(request.getPassword()));
        }

        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }
}