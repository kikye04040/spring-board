package com.spring.springboard.domain.board.controller;

import com.spring.springboard.domain.board.dto.request.BoardRequest;
import com.spring.springboard.domain.board.dto.response.BoardResponse;
import com.spring.springboard.domain.board.entity.Board;
import com.spring.springboard.domain.board.service.BoardService;
import com.spring.springboard.domain.user.entity.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(
            @RequestBody @Valid BoardRequest request,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        Board board = boardService.createBoard(request, authUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BoardResponse(board));
    }
}
