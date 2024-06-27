package com.io.hhplus.registerlecture.datasource.lecture.repository.impl;

import com.io.hhplus.registerlecture.business.lecture.model.LectureStatus;
import com.io.hhplus.registerlecture.datasource.lecture.mapper.LectureMapper;
import com.io.hhplus.registerlecture.datasource.lecture.mapper.LectureOptionMapper;
import com.io.hhplus.registerlecture.datasource.lecture.model.Lecture;
import com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption;
import com.io.hhplus.registerlecture.datasource.lecture.repository.LectureJpaRepository;
import com.io.hhplus.registerlecture.datasource.lecture.repository.LectureOptionJpaRepository;
import com.io.hhplus.registerlecture.global.utils.DateUtils;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LectureOptionRepositoryImplTest {

    @Autowired
    LectureOptionJpaRepository lectureOptionJpaRepository;

    @Autowired
    LectureJpaRepository lectureJpaRepository;

    private final LectureMapper lectureMapper = new LectureMapper();
    private final LectureOptionMapper lectureOptionMapper = new LectureOptionMapper();

    /**
     * LectureOption 테이블에 데이터 없을 시 빈 Optional 객체 return.
     */
    @Test
    void findById_when_data_is_empty_return_empty() {
        // given
        long lectureOptionId = 1;
        // when
        Optional<LectureOption> lectureOption = lectureOptionJpaRepository.findById(lectureOptionId);
        // then
        assertTrue(lectureOption.isEmpty());
    }

    /**
     * LectureOption 데이터 저장하기.
     */
    @Test
    void save_lectureOption() {
        // given
        Lecture lecture = Lecture.builder()
                .id(null)
                .name("과학")
                .useYn("Y")
                .status("OPEN")
                .build();
        Lecture savedLecture = lectureJpaRepository.save(lecture);

        LectureOption lectureOption = LectureOption.builder()
                .id(null)
                .lecture(savedLecture)
                .registerBeginAt(DateUtils.createTemporalDateByIntParameters(2024, 3, 24, 11, 11, 11))
                .registerEndAt(DateUtils.createTemporalDateByIntParameters(2024, 3, 27, 23, 59, 59))
                .lectureDatetime(DateUtils.createTemporalDateByIntParameters(2024, 4, 1, 13,0, 0))
                .capacityLimit(30)
                .useYn("Y")
                .build();

        // when
        LectureOption savedLectureOption = lectureOptionJpaRepository.save(lectureOption);

        // then
        assertEquals(savedLecture.getId(), lectureOption.getLecture().getId());
        assertEquals(lectureOption.getCapacityLimit(), savedLectureOption.getCapacityLimit());
        assertEquals(lectureOption.getLectureDatetime(), savedLectureOption.getLectureDatetime());
    }

    /**
     * findByIdAndLectureId lectureId, lectureOptionId로 잘 가져오는지 확인
     */
    @Test
    void findByIdAndLectureId_select_correct_data() {
        long lectureOptionId = 1;
        long lectureId = 1;

        Lecture lecture = Lecture.builder()
                .id(null)
                .name("과학")
                .useYn("Y")
                .status("OPEN")
                .build();
        Lecture savedLecture = lectureJpaRepository.save(lecture);

        LectureOption lectureOption = LectureOption.builder()
                .id(null)
                .lecture(savedLecture)
                .registerBeginAt(DateUtils.createTemporalDateByIntParameters(2024, 3, 24, 11, 11, 11))
                .registerEndAt(DateUtils.createTemporalDateByIntParameters(2024, 3, 27, 23, 59, 59))
                .lectureDatetime(DateUtils.createTemporalDateByIntParameters(2024, 4, 1, 13,0, 0))
                .capacityLimit(30)
                .useYn("Y")
                .build();

        lectureOptionJpaRepository.save(lectureOption);

        Optional<LectureOption> realLectureOption = lectureOptionJpaRepository.findByIdAndLectureId(lectureOptionId, lectureId);

        assertTrue(realLectureOption.isPresent());
        assertEquals(lectureOptionId, realLectureOption.get().getId());
        assertEquals(lectureId, realLectureOption.get().getLecture().getId());
    }

    @Test
    void test_save_using_mapper() {
        long lectureOptionId = 1;
        long lectureId = 1;

        com.io.hhplus.registerlecture.business.lecture.model.Lecture lecture = com.io.hhplus.registerlecture.business.lecture.model.Lecture.builder()
                .id(null)
                .name("과학")
                .useYn("Y")
                .status(LectureStatus.OPEN)
                .build();
        com.io.hhplus.registerlecture.business.lecture.model.Lecture savedLecture = lectureMapper.toDto(lectureJpaRepository.save(lectureMapper.toEntity(lecture)));

        com.io.hhplus.registerlecture.business.lecture.model.LectureOption lectureOption = com.io.hhplus.registerlecture.business.lecture.model.LectureOption.builder()
                .id(null)
                .lectureId(savedLecture.getId())
                .registerBeginAt(DateUtils.createTemporalDateByIntParameters(2024, 3, 24, 11, 11, 11))
                .registerEndAt(DateUtils.createTemporalDateByIntParameters(2024, 3, 27, 23, 59, 59))
                .lectureDatetime(DateUtils.createTemporalDateByIntParameters(2024, 4, 1, 13,0, 0))
                .capacityLimit(30)
                .useYn("Y")
                .build();

        com.io.hhplus.registerlecture.business.lecture.model.LectureOption savedLectureOption = lectureOptionMapper.toDto(lectureOptionJpaRepository.save(lectureOptionMapper.toEntity(lectureOption)));

        assertEquals(lectureOptionId, savedLectureOption.getId());
        assertEquals(lectureId, savedLectureOption.getLectureId());
    }
}