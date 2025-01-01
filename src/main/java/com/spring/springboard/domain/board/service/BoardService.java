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
}
