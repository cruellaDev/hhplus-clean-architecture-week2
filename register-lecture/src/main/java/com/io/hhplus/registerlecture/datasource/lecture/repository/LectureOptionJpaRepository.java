package com.io.hhplus.registerlecture.datasource.lecture.repository;

import com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureOptionJpaRepository extends JpaRepository<LectureOption, Long> {
    Optional<LectureOption> findByIdAndLectureId(long lectureOptionId, long lectureId);
}
