package com.autowise.demo.controller;

import com.autowise.demo.dto.AdminRegisterDto;
import com.autowise.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;

    // ✅ Only ADMIN can create another ADMIN
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> registerAdmin(@Valid @RequestBody AdminRegisterDto request) {
        authService.registerAdmin(request);
        return ResponseEntity.ok().build();
    }
}
