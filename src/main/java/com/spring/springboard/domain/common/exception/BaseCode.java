package com.spring.springboard.domain.common.exception;

import com.spring.springboard.domain.common.dto.CodeResponseDto;

public interface BaseCode {
    CodeResponseDto getReasonHttpStatus();
}
