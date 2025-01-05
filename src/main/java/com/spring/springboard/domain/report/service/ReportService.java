package com.spring.springboard.domain.report.service;

import com.spring.springboard.domain.board.entity.Board;
import com.spring.springboard.domain.board.repository.BoardRepository;
import com.spring.springboard.domain.common.enums.ErrorStatus;
import com.spring.springboard.domain.common.exception.ApiException;
import com.spring.springboard.domain.report.entity.Report;
import com.spring.springboard.domain.report.repository.ReportRepository;
import com.spring.springboard.domain.user.entity.CustomUserDetails;
import com.spring.springboard.domain.user.entity.User;
import com.spring.springboard.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Report createReport(Long boardId, String reason, CustomUserDetails authUser) {
        User reporterUser = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_BOARD));

        Report report = new Report(board.getAuthor(), reporterUser, board, reason);
        return reportRepository.save(report);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Report resloveReport(Long reportId, CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_REPORT));

        report.resolve();
        return reportRepository.save(report);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<Report> getUnsolvedReports(CustomUserDetails authUser) {
        userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_USER));

        return reportRepository.findAllByResolvedFalse();
    }
}
