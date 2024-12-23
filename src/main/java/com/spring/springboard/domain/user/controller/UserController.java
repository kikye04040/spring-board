package com.spring.springboard.domain.user.controller;

import com.spring.springboard.domain.common.dto.ApiResponse;
import com.spring.springboard.domain.user.dto.request.PasswordChangeRequest;
import com.spring.springboard.domain.user.dto.response.UserResponse;
import com.spring.springboard.domain.user.entity.CustomUserDetails;
import com.spring.springboard.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestBody @Valid PasswordChangeRequest request) {
        userService.changePassword(authUser, request);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(userService.getUser(userId)));
    }
}
