package com.spring.springboard.domain.report.controller;

import com.spring.springboard.domain.report.dto.request.ReportRequest;
import com.spring.springboard.domain.report.entity.Report;
import com.spring.springboard.domain.report.service.ReportService;
import com.spring.springboard.domain.user.entity.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/{boardId}")
    public ResponseEntity<Report> createReport(
            @PathVariable Long boardId,
            @RequestBody @Valid ReportRequest request,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        Report report = reportService.createReport(boardId, request.getReason(), authUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    @PutMapping("/{reportId}/resolve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Report> resolveReport(
            @PathVariable Long reportId,
            @AuthenticationPrincipal CustomUserDetails authUser) {
        Report report = reportService.resolveReport(reportId, authUser);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/unsolved")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Report>> getUnsolvedReports(
            @AuthenticationPrincipal CustomUserDetails authUser
    ) {
        List<Report> reports = reportService.getUnsolvedReports(authUser);
        return ResponseEntity.ok(reports);
    }
}
