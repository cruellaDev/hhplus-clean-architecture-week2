package com.io.hhplus.registerlecture.datasource.lecture.repository.impl;

import com.io.hhplus.registerlecture.business.lecture.model.LectureChangeHistory;
import com.io.hhplus.registerlecture.business.lecture.repository.LectureChangeHistoryRepository;
import com.io.hhplus.registerlecture.datasource.lecture.mapper.LectureChangeHistoryMapper;
import com.io.hhplus.registerlecture.datasource.lecture.repository.LectureChangeHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LectureChangeHistoryRepositoryImpl implements LectureChangeHistoryRepository {

    private final LectureChangeHistoryJpaRepository lectureChangeHistoryJpaRepository;
    private final LectureChangeHistoryMapper lectureChangeHistoryMapper;

    @Override
    public LectureChangeHistory save(LectureChangeHistory lectureChangeHistory) {
        return lectureChangeHistoryMapper.toDto(lectureChangeHistoryJpaRepository.save(lectureChangeHistoryMapper.toEntity(lectureChangeHistory)));
    }
}
