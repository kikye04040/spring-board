package com.spring.springboard.domain.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class CodeResponseDto {
    private HttpStatus httpStatus;
    private Integer statusCode;
    private String message;
}
