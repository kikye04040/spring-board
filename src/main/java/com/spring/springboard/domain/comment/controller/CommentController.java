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
}
