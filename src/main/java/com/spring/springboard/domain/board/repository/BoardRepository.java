package com.spring.springboard.domain.board.repository;

import com.spring.springboard.domain.board.entity.Board;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Modifying
    @Query("UPDATE Board b SET b.title = :title, b.description = :description WHERE b.id = :boardId")
    void updateBoard(@Param("boardId") Long boardId,
                     @Param("title") String title,
                     @Param("description") String description);

    Optional<Board> findByIdIncludingDeleted(Long boardId);
}
