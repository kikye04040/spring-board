package com.spring.springboard.domain.common.enums;

import com.spring.springboard.domain.common.dto.CodeResponseDto;
import com.spring.springboard.domain.common.exception.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {
    TEST_ERROR(HttpStatus.BAD_REQUEST, 400, "ApiException 예외 처리 테스트");

    private final HttpStatus httpStatus;
    private final Integer statusCode;
    private final String message;

    @Override
    public CodeResponseDto getReasonHttpStatus() {
        return CodeResponseDto.builder()
                .statusCode(statusCode)
                .httpStatus(httpStatus)
                .message(message)
                .build();
    }
}
