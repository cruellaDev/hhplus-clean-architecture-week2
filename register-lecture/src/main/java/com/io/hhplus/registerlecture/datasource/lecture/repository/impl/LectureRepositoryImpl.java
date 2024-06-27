package com.io.hhplus.registerlecture.datasource.lecture.repository.impl;

import com.io.hhplus.registerlecture.business.lecture.dto.LectureDto;
import com.io.hhplus.registerlecture.business.lecture.repository.LectureRepository;
import com.io.hhplus.registerlecture.datasource.lecture.mapper.LectureMapper;
import com.io.hhplus.registerlecture.datasource.lecture.repository.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;
    private final LectureMapper lectureMapper;

    @Override
    public Optional<LectureDto> findById(long lectureId) {
        return lectureMapper.toDto(lectureJpaRepository.findById(lectureId));
    }

    @Override
    public LectureDto save(LectureDto lectureDto) {
        return lectureMapper.toDto(lectureJpaRepository.save(lectureMapper.toEntity(lectureDto)));
    }
}
