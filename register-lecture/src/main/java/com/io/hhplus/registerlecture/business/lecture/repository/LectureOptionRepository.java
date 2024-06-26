package com.io.hhplus.registerlecture.business.lecture.repository;

import com.io.hhplus.registerlecture.business.lecture.model.LectureOption;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectureOptionRepository {
    Optional<LectureOption> findByIdAndLectureId(long lectureOptionId, long lectureId);
}
