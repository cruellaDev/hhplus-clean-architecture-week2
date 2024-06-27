package com.io.hhplus.registerlecture.business.lecture.repository;

import com.io.hhplus.registerlecture.business.lecture.dto.LectureChangeHistoryDto;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureChangeHistoryRepository {
    LectureChangeHistoryDto save(LectureChangeHistoryDto lectureChangeHistoryDto);
}
