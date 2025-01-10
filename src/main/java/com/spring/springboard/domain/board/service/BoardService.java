package com.spring.springboard.domain.board.service;

import com.spring.springboard.domain.board.dto.request.BoardRequest;
import com.spring.springboard.domain.board.dto.response.BoardResponse;
import com.spring.springboard.domain.board.entity.Board;
import com.spring.springboard.domain.board.repository.BoardRepository;
import com.spring.springboard.domain.common.enums.ErrorStatus;
import com.spring.springboard.domain.common.exception.ApiException;
import com.spring.springboard.domain.user.entity.CustomUserDetails;
import com.spring.springboard.domain.user.entity.User;
import com.spring.springboard.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Board createBoard(BoardRequest request, CustomUserDetails authUser) {
        User author = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        Board board = new Board(request.getTitle(), request.getDescription(), author);

        return boardRepository.save(board);
    }

    @Transactional
    public BoardResponse getBoard(Long boardId, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_BOARD));

        return new BoardResponse(board);
    }

    @Transactional
    public Page<BoardResponse> getAllBoards(Pageable pageable, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        return boardRepository.findAll(pageable).map(BoardResponse::new);
    }

    @Transactional
    public void updateBoard(Long boardId, BoardRequest request, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_BOARD));

        boardRepository.updateBoard(boardId, request.getTitle(), request.getDescription());
    }

    public void deleteBoard(Long boardId, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_BOARD));
        board.delete();
    }

    public void restoreBoard(Long boardId, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        Board board = boardRepository.findByIdIncludingDeleted(boardId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_BOARD));
        board.restore();
    }

    public void incrementViewCount(Long boardId, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_BOARD));
        board.incrementViewCount();
    }

    public void incrementLikeCount(Long boardId, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_BOARD));
        board.incrementLikeCount();
    }

    public void incrementDislikeCount(Long boardId, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_BOARD));
        board.incrementDislikeCount();
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> getLatestBoards() {
        return boardRepository.findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(BoardResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
