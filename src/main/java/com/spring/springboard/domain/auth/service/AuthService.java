package com.spring.springboard.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${sprout.market.admin.key}")
    private String adminKey;


}
