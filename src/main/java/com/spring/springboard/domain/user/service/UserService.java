package com.spring.springboard.domain.user.service;

import com.spring.springboard.domain.common.enums.ErrorStatus;
import com.spring.springboard.domain.common.exception.ApiException;
import com.spring.springboard.domain.user.dto.response.UserResponse;
import com.spring.springboard.domain.user.dto.request.PasswordChangeRequest;
import com.spring.springboard.domain.user.entity.CustomUserDetails;
import com.spring.springboard.domain.user.entity.User;
import com.spring.springboard.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void changePassword(CustomUserDetails authUser, PasswordChangeRequest request) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        // 기존 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_PASSWORD);
        }

        // 새 비밀번호가 기존 비밀번호와 일치하면 오류 발생 (암호화하지 않은 상태로 확인)
        if (request.getOldPassword().equals(request.getNewPassword())) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_NEW_PASSWORD);
        }

        // 새 비밀번호 암호화 후 DB 에 저장
        user.changePassword(passwordEncoder.encode(request.getNewPassword()));
    }

    public UserResponse getUser(Long userId) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        // 유저 정보 반환
        return new UserResponse(user.getId(), user.getEmail());
    }
}
