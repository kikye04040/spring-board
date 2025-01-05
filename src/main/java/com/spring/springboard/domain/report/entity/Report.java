package com.spring.springboard.domain.report.entity;

import com.spring.springboard.domain.board.entity.Board;
import com.spring.springboard.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE reports SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_user_id")
    private User reportedUser; // 신고된 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_user_id")
    private User reporterUser; // 신고한 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board; // 신고된 게시글

    @Column(nullable = false)
    private String reason; // 신고 이유

    @Column(nullable = false)
    private boolean resolved = false; // 처리 상태 (해결 여부)

    @Column(nullable = false)
    private boolean deleted = false;

    public Report(User reportedUser, User reporterUser, Board board, String reason) {
        this.reportedUser = reportedUser;
        this.reporterUser = reporterUser;
        this.board = board;
        this.reason = reason;
    }

    public void resolve() {
        this.resolved = true;
    }
}
