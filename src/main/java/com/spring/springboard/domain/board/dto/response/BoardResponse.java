package com.spring.springboard.domain.board.dto.response;

import com.spring.springboard.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BoardResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final String author;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static BoardResponse fromEntity(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getDescription(),
                board.getAuthor().getUsername(),
                board.getCreatedAt(),
                board.getModifiedAt()
        );
    }

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.author = board.getAuthor().getUsername();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
