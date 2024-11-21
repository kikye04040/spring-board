package com.spring.springboard.domain.user.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserRole {
    ADMIN, USER;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().contentEquals(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + role));
    }
}
