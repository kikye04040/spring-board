package com.spring.springboard.domain.auth.controller;

import com.spring.springboard.domain.auth.dto.request.AdminSignupRequest;
import com.spring.springboard.domain.auth.dto.request.EmailVerificationRequest;
import com.spring.springboard.domain.auth.dto.request.SigninRequest;
import com.spring.springboard.domain.auth.dto.request.SignupRequest;
import com.spring.springboard.domain.auth.dto.response.SigninResponse;
import com.spring.springboard.domain.auth.dto.response.SignupResponse;
import com.spring.springboard.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/signup")
    public SignupResponse signup(@Valid @RequestBody SignupRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/auth/signin")
    public SigninResponse signin(@Valid @RequestBody SigninRequest request) {
        return authService.signin(request);
    }

    @PostMapping("/auth/email")
    public void emailVerification(@Valid @RequestBody EmailVerificationRequest request) {
        authService.verifyEmail(request);
    }

    @PostMapping("/admin/signup")
    public SignupResponse adminSignup(@Valid @RequestBody AdminSignupRequest request) {
        return authService.adminSignup(request);
    }

    @PostMapping("/admin/signin")
    public SigninResponse adminSignin(@Valid @RequestBody SigninRequest request) {
        return authService.adminSignin(request);
    }
}
