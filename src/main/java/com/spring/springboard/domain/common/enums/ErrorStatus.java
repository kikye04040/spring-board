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
    INVALID_PASSWORD_FOAM(HttpStatus.BAD_REQUEST, 400, "비밀번호는 최소 8자 이상이며, 대문자, 소문자, 숫자, 특수 문자를 포함해야 합니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 404, "해당 유저를 찾을 수 없습ㄴ디ㅏ."),
    BAD_REQUEST_PASSWORD(HttpStatus.BAD_REQUEST, 400, "비밀번호가 일치하지 않습니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, 403, "관리자만 접근이 가능합니다."),
    INTERNAL_SERVER_UNHANDLING(HttpStatus.INTERNAL_SERVER_ERROR, 500,  "알 수 없는 오류가 발생했습니다."),
    UNAUTHORIZED_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 401, "만료된 JWT 토큰입니다."),
    BAD_REQUEST_UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST,400,"지원되지 않는 JWT 토큰입니다."),
    BAD_REQUEST_INVALID_TOKEN(HttpStatus.UNAUTHORIZED,401,"유효하지 않는 JWT 서명입니다."),
    BAD_REQUEST_ILLEGAL_TOKEN(HttpStatus.BAD_REQUEST,400,"잘못된 JWT 토큰입니다."),
    BAD_REQUEST_NEW_PASSWORD(HttpStatus.BAD_REQUEST, 400, "새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");

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
