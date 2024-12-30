package com.spring.springboard.domain.admin.controller;

import com.spring.springboard.domain.admin.service.AdminService;
import com.spring.springboard.domain.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/users")
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(Pageable pageable) {
        Page<UserResponse> users = adminService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/restore/{userId}")
    public ResponseEntity<Void> restoreUser(@PathVariable Long userId) {
        adminService.restoreUser(userId);
        return ResponseEntity.noContent().build();
    }
}
