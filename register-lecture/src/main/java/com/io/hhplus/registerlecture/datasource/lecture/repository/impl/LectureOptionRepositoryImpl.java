package com.io.hhplus.registerlecture.datasource.lecture.repository.impl;

import com.io.hhplus.registerlecture.business.lecture.model.LectureOption;
import com.io.hhplus.registerlecture.business.lecture.repository.LectureOptionRepository;
import com.io.hhplus.registerlecture.datasource.lecture.mapper.LectureOptionMapper;
import com.io.hhplus.registerlecture.datasource.lecture.repository.LectureOptionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LectureOptionRepositoryImpl implements LectureOptionRepository {

    private final LectureOptionJpaRepository lectureOptionJpaRepository;
    private final LectureOptionMapper lectureOptionMapper;

    @Override
    public Optional<LectureOption> findByIdAndLectureId(long lectureOptionId, long lectureId) {
        return lectureOptionMapper.toDto(lectureOptionJpaRepository.findByIdAndLectureId(lectureOptionId, lectureId));
    }
}
