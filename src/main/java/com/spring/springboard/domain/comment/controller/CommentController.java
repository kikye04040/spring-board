package com.spring.springboard.domain.comment.controller;

import com.spring.springboard.domain.comment.dto.request.CommentRequest;
import com.spring.springboard.domain.comment.entity.Comment;
import com.spring.springboard.domain.comment.service.CommentService;
import com.spring.springboard.domain.user.entity.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @PathVariable Long boardId,
            @RequestBody @Valid CommentRequest request,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        Comment comment = commentService.createComment(boardId, request.getContent(), authUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequest request,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        Comment comment = commentService.updateComment(commentId, request.getContent(), authUser);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        commentService.deleteComment(commentId, authUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByBoardId(
            @PathVariable Long boardId,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        List<Comment> comments = commentService.getCommentsByBoardId(boardId, authUser);
        return ResponseEntity.ok(comments);
    }
}
