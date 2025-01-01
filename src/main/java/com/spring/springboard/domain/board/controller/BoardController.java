package com.spring.springboard.domain.board.controller;

import com.spring.springboard.domain.board.dto.request.BoardRequest;
import com.spring.springboard.domain.board.dto.response.BoardResponse;
import com.spring.springboard.domain.board.entity.Board;
import com.spring.springboard.domain.board.service.BoardService;
import com.spring.springboard.domain.user.entity.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> getBoard(
            @PathVariable Long boardId,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        BoardResponse boardResponse = boardService.getBoard(boardId, authUser);
        return ResponseEntity.ok(boardResponse);
    }

    @GetMapping
    public ResponseEntity<Page<BoardResponse>> getAllBoards(Pageable pageable, CustomUserDetails authUser) {
        Page<BoardResponse> boardResponses = boardService.getAllBoards(pageable, authUser);
        return ResponseEntity.ok(boardResponses);
    }
}
