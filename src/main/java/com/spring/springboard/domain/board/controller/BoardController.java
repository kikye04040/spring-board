package com.spring.springboard.domain.board.controller;

import com.spring.springboard.domain.board.dto.request.BoardRequest;
import com.spring.springboard.domain.board.dto.response.BoardResponse;
import com.spring.springboard.domain.board.entity.Board;
import com.spring.springboard.domain.board.service.BoardService;
import com.spring.springboard.domain.user.entity.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponse> updateBoard(
            @PathVariable Long boardId,
            @RequestBody @Valid BoardRequest request,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        boardService.updateBoard(boardId, request, authUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<BoardResponse> deleteBoard(
            @PathVariable Long boardId,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        boardService.deleteBoard(boardId, authUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{boardId}/restore")
    public ResponseEntity<BoardResponse> restoreBoard(
            @PathVariable Long boardId,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        boardService.restoreBoard(boardId, authUser);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{boardId}/view")
    public ResponseEntity<Void> incrementViewCount(
            @PathVariable Long boardId,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        boardService.incrementViewCount(boardId, authUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{boardId}/like")
    public ResponseEntity<Void> incrementLikeCount(
            @PathVariable Long boardId,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        boardService.incrementLikeCount(boardId, authUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{boardId}/dislike")
    public ResponseEntity<Void> incrementDislikeCount(
            @PathVariable Long boardId,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        boardService.incrementDislikeCount(boardId, authUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/latest")
    public ResponseEntity<Page<BoardResponse>> getLatestBoards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<BoardResponse> latestBoards = boardService.getLatestBoards(pageable);
        return ResponseEntity.ok(latestBoards);
    }
}
