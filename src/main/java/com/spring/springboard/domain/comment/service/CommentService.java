package com.spring.springboard.domain.comment.service;

import com.spring.springboard.domain.board.entity.Board;
import com.spring.springboard.domain.board.repository.BoardRepository;
import com.spring.springboard.domain.comment.entity.Comment;
import com.spring.springboard.domain.comment.repository.CommentRepository;
import com.spring.springboard.domain.common.enums.ErrorStatus;
import com.spring.springboard.domain.common.exception.ApiException;
import com.spring.springboard.domain.user.entity.CustomUserDetails;
import com.spring.springboard.domain.user.entity.User;
import com.spring.springboard.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment createComment(Long boardId, String content, CustomUserDetails authUser) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_BOARD));
        User user = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        Comment comment = new Comment(content, board, user);
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long commentId, String content, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_COMMENT));

        comment.update(content);
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_COMMENT));

        if (!comment.getAuthor().getEmail().equals(authUser.getEmail())) {
            throw new ApiException(ErrorStatus.FORBIDDEN_COMMENT_ACCESS);
        }

        comment.softDelete();
    }

    @Transactional
    public List<Comment> getCommentsByBoardId(Long boardId, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        return commentRepository.findAllByBoardId(boardId);
    }

    @Transactional
    public List<Comment> getCommentsByLikes(Long boardId, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        return commentRepository.findByBoardIdOrderByLikeCountDesc(boardId);
    }

    @Transactional
    public void deleteCommentAsBoardAuthor(long commentId, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_COMMENT));

        Board board = comment.getBoard();

        if (!board.getAuthor().getEmail().equals(authUser.getEmail())) {
            throw new ApiException(ErrorStatus.FORBIDDEN_COMMENT_ACCESS);
        }

        comment.softDelete();
    }

    @Transactional
    public void incrementLikeCount(Long commentId, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_COMMENT));

        comment.incrementLikeCount();
    }
}
