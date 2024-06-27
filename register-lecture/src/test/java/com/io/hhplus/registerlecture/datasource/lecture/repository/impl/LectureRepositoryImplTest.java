package com.io.hhplus.registerlecture.datasource.lecture.repository.impl;

import com.io.hhplus.registerlecture.datasource.lecture.model.Lecture;
import com.io.hhplus.registerlecture.datasource.lecture.repository.LectureJpaRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DB 연결 및 JPA 테스트
 */
@ActiveProfiles("test")
@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LectureRepositoryImplTest {

    @Autowired
    LectureJpaRepository lectureJpaRepository;

    /**
     * Lecture 테이블에 데이터 없을 시 빈 Optional 객체 return.
     */
    @Test
    void findById_when_data_is_empty_then_return_empty() {
        long id = 1L;
        Optional<Lecture> lecture = lectureJpaRepository.findById(id);
        assertTrue(lecture.isEmpty());
    }

    /**
     * Lecture 테이블 신규 데이터 생성 확인
     */
    @Test
    void save_lecture() {
        // given
        Lecture lecture = Lecture.builder()
                .id(null)
                .name("강의명")
                .status("OPEN")
                .useYn("Y")
                .build();

        // when
        Lecture savedLecture = lectureJpaRepository.save(lecture);
        // then
        assertEquals(lecture.getUseYn(), savedLecture.getUseYn());
        assertEquals(lecture.getName(), savedLecture.getName());
        assertEquals(lecture.getStatus(), savedLecture.getStatus());
    }

    /**
     * 사용자 목록 모두 저장 후 모두 조회
     */
    @Test
    void save_all_lecture_and_find_all_lecture() {
        // given
        List<Lecture> lectureList = List.of(
                Lecture.builder()
                        .id(null)
                        .name("제1강의")
                        .status("OPEN")
                        .useYn("Y")
                        .build(),
                Lecture.builder()
                        .id(null)
                        .name("제2강의")
                        .status("CLOSE")
                        .useYn("Y")
                        .build(),
                Lecture.builder()
                        .id(null)
                        .name("제3강의")
                        .status("CLOSE")
                        .useYn("N")
                        .build()
        );
        lectureJpaRepository.saveAll(lectureList);
        List<Lecture> savedLectureList = lectureJpaRepository.findAll();
        assertEquals(3, savedLectureList.size());
    }

}