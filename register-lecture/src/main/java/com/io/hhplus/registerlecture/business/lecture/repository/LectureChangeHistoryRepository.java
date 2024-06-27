package com.io.hhplus.registerlecture.business.lecture.repository;

import com.io.hhplus.registerlecture.business.lecture.model.LectureChangeHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureChangeHistoryRepository {
    LectureChangeHistory save(LectureChangeHistory lectureChangeHistory);
}
