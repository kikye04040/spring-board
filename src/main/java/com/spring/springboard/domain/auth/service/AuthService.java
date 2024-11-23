package com.spring.springboard.domain.auth.service;

import com.spring.springboard.config.JwtUtil;
import com.spring.springboard.domain.auth.dto.request.AdminSignupRequest;
import com.spring.springboard.domain.auth.dto.request.SigninRequest;
import com.spring.springboard.domain.auth.dto.request.SignupRequest;
import com.spring.springboard.domain.auth.dto.response.SigninResponse;
import com.spring.springboard.domain.auth.dto.response.SignupResponse;
import com.spring.springboard.domain.common.enums.ErrorStatus;
import com.spring.springboard.domain.common.exception.ApiException;
import com.spring.springboard.domain.user.entity.User;
import com.spring.springboard.domain.user.enums.UserRole;
import com.spring.springboard.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public SignupResponse signup(SignupRequest request) {
        // 이메일 유효성 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_EMAIL);
        }

        // 비밀번호 유효성 검사
        if (!isPasswordValid(request.getPassword())) {
            throw new ApiException(ErrorStatus.INVALID_PASSWORD_FOAM);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 엔티티에 유저 정보 저장
        User newUser = new User(
                request.getUsername(),
                request.getEmail(),
                encodedPassword,
                request.getNickname(),
                request.getPhoneNumber(),
                request.getAddress(),
                UserRole.USER
        );

        // DB 에 저장
        userRepository.save(newUser);

        // 토큰 생성
        String bearerToken = jwtUtil.createToken(newUser.getId(), newUser.getEmail(), newUser.getUserRole());

        // 토큰 반환
        return new SignupResponse(bearerToken);
    }

    public SigninResponse signin(@Valid SigninRequest request) {
        return null;
    }

    public SignupResponse adminSignup(@Valid AdminSignupRequest request) {
        return null;
    }

    public SigninResponse adminSignin(@Valid SigninRequest request) {
        return null;
    }

    private boolean isPasswordValid(String password) {
        return StringUtils.hasText(password) && Pattern.matches(PASSWORD_PATTERN, password);
    }
}
