package com.spring.springboard.domain.common.enums;

import com.spring.springboard.domain.common.dto.CodeResponseDto;
import com.spring.springboard.domain.common.exception.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {
    BAD_REQUEST_EMAIL(HttpStatus.BAD_REQUEST, 400, "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD_FOAM(HttpStatus.BAD_REQUEST, 400, "비밀번호는 최소 8자 이상이며, 대문자, 소문자, 숫자, 특수 문자를 포함해야 합니다.");

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
