package com.io.hhplus.registerlecture.business.lecture.repository;

import com.io.hhplus.registerlecture.business.lecture.dto.LectureOptionDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectureOptionRepository {
    Optional<LectureOptionDto> findByIdAndLectureId(long lectureOptionId, long lectureId);
    LectureOptionDto save(LectureOptionDto lectureOptionDto);
}
