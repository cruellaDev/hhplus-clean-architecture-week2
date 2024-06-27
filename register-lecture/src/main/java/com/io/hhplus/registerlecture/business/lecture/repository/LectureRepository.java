package com.io.hhplus.registerlecture.business.lecture.repository;

import com.io.hhplus.registerlecture.business.lecture.dto.LectureDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectureRepository {
    Optional<LectureDto> findById(long lectureId);
    LectureDto save(LectureDto lectureDto);
}
