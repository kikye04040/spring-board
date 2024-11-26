package com.spring.springboard.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.springboard.domain.common.dto.ApiResponse;
import com.spring.springboard.domain.common.enums.ErrorStatus;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtExceptionHandler {
    public static void handleJwtException(HttpServletResponse response, Exception ex) throws IOException {
        ErrorStatus errorStatus = ErrorStatus.INTERNAL_SERVER_UNHANDLING;

        if (ex instanceof JwtException) {
            errorStatus = ErrorStatus.UNAUTHORIZED_EXPIRED_TOKEN;
        }
        else if (ex instanceof UnsupportedJwtException) {
            errorStatus = ErrorStatus.BAD_REQUEST_UNSUPPORTED_TOKEN;
        }
        else if (ex instanceof MalformedJwtException) {
            errorStatus = ErrorStatus.BAD_REQUEST_INVALID_TOKEN;
        }
        else if (ex instanceof IllegalArgumentException) {
            errorStatus = ErrorStatus.BAD_REQUEST_ILLEGAL_TOKEN;
        }

        response.setStatus(errorStatus.getStatusCode());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = new ObjectMapper().writeValueAsString(ApiResponse.createError(errorStatus.getMessage(), errorStatus.getStatusCode()));
        response.getWriter().write(json);
    }
}
