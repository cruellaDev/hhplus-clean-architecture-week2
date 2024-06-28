package com.io.hhplus.registerlecture.datasource.lecture.repository.impl;

import com.io.hhplus.registerlecture.business.lecture.dto.LectureOptionDto;
import com.io.hhplus.registerlecture.business.lecture.repository.LectureOptionRepository;
import com.io.hhplus.registerlecture.datasource.lecture.mapper.LectureOptionMapper;
import com.io.hhplus.registerlecture.datasource.lecture.repository.LectureOptionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LectureOptionRepositoryImpl implements LectureOptionRepository {

    private final LectureOptionJpaRepository lectureOptionJpaRepository;
    private final LectureOptionMapper lectureOptionMapper;

    @Override
    public Optional<LectureOptionDto> findByIdAndLectureId(long lectureOptionId, long lectureId) {
        return lectureOptionMapper.toDto(lectureOptionJpaRepository.findByIdAndLectureIdWithPessimisticLock(lectureOptionId, lectureId));
    }

    @Override
    public LectureOptionDto save(LectureOptionDto lectureOptionDto) {
        return lectureOptionMapper.toDto(lectureOptionJpaRepository.save(lectureOptionMapper.toEntity(lectureOptionDto)));
    }

    @Override
    public List<LectureOptionDto> findAllAvailable(Date sysDate) {
        return lectureOptionJpaRepository.findBydUseYn("Y")
                .stream()
                .filter(lectureOption -> Objects.equals(lectureOption.getLecture().getUseYn(), "Y")
                     && lectureOption.getLectureDatetime().compareTo(sysDate) > 0
                     && lectureOption.getRegisterBeginAt().compareTo(sysDate) <= 0
                     && lectureOption.getRegisterEndAt().compareTo(sysDate) >= 0
                     && lectureOption.getCapacityLimit().compareTo(lectureOption.getCurrentCapacity()) > 0)
                .map(lectureOptionMapper::toDto)
                .collect(Collectors.toList());
    }
}
