package com.io.hhplus.registerlecture.datasource.lecture.repository.impl;

import com.io.hhplus.registerlecture.datasource.lecture.model.LectureChangeHistory;
import com.io.hhplus.registerlecture.datasource.lecture.repository.LectureChangeHistoryJpaRepository;
import com.io.hhplus.registerlecture.global.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LectureChangeHistoryRepositoryImplTest {

    @Autowired
    LectureChangeHistoryJpaRepository lectureChangeHistoryJpaRepository;

    /**
     * DB 확인 및 LectureChangeHistory 데이터 저장하기
     */
    @Test
    void save_lectureChangeHistory() {
        // given
        LectureChangeHistory lectureChangeHistory = LectureChangeHistory.builder()
                .id(null)
                .lectureId(1L)
                .lectureOptionId(1L)
                .lectureDatetime(DateUtils.createTemporalDateByIntParameters(2024, 3, 25, 11, 18, 55))
                .capacityLimit(30)
                .lectureName("강의이름")
                .registerBeginAt(DateUtils.createTemporalDateByIntParameters(2024, 1, 25, 11, 18, 55))
                .registerEndAt(DateUtils.createTemporalDateByIntParameters(2024, 2, 25, 11, 18, 55))
                .status("OPEN")
                .build();

        // when
        LectureChangeHistory savedLectureChangeHistory = lectureChangeHistoryJpaRepository.save(lectureChangeHistory);

        // then
        assertNotNull(savedLectureChangeHistory.getId());
        assertEquals(lectureChangeHistory.getLectureName(), savedLectureChangeHistory.getLectureName());
    }

}