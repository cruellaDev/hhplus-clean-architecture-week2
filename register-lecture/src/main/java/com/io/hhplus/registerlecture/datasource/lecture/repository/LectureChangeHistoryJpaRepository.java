package com.io.hhplus.registerlecture.datasource.lecture.repository;

import com.io.hhplus.registerlecture.datasource.lecture.model.LectureChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureChangeHistoryJpaRepository extends JpaRepository<LectureChangeHistory, Long> {
}
