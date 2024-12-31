package com.spring.springboard.domain.admin.service;

import com.spring.springboard.domain.common.enums.ErrorStatus;
import com.spring.springboard.domain.common.exception.ApiException;
import com.spring.springboard.domain.user.dto.response.UserResponse;
import com.spring.springboard.domain.user.entity.User;
import com.spring.springboard.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    // 모든 유저 조회
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserResponse::new);
    }

    // 삭제된 유저 복원
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void restoreUser(Long userId) {
        User user = userRepository.findByIdIncludingDeleted(userId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));
        user.activate();
    }
}
