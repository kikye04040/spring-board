package com.spring.springboard.domain.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;
}
