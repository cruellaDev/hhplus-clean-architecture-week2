package com.io.hhplus.registerlecture.datasource.lecture.repository.impl;

import com.io.hhplus.registerlecture.business.lecture.repository.LectureChangeHistoryRepository;
import com.io.hhplus.registerlecture.datasource.lecture.repository.LectureChangeHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LectureChangeHistoryRepositoryImpl implements LectureChangeHistoryRepository {

    private final LectureChangeHistoryJpaRepository lectureChangeHistoryJpaRepository;

}
