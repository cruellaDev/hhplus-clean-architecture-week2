package com.io.hhplus.registerlecture.datasource.lecture.mapper;

import com.io.hhplus.registerlecture.business.lecture.dto.LectureOptionDto;
import com.io.hhplus.registerlecture.datasource.lecture.model.Lecture;
import com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption;
import com.io.hhplus.registerlecture.global.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LectureOptionMapper implements EntityMapper<LectureOptionDto, LectureOption> {

    @Override
    public LectureOptionDto toDto(LectureOption lectureOption) {
        return LectureOptionDto.builder()
                .id(lectureOption.getId())
                .lectureId(lectureOption.getLecture().getId())
                .registerBeginAt(lectureOption.getRegisterBeginAt())
                .registerEndAt(lectureOption.getRegisterEndAt())
                .lectureDatetime(lectureOption.getLectureDatetime())
                .capacityLimit(lectureOption.getCapacityLimit())
                .useYn(lectureOption.getUseYn())
                .createdAt(lectureOption.getAuditSection().getCreatedAt())
                .modifiedAt(lectureOption.getAuditSection().getModifiedAt())
                .build();
    }

    @Override
    public LectureOption toEntity(LectureOptionDto lectureOptionDto) {
        return LectureOption.builder()
                .id(lectureOptionDto.getId())
                .lecture(Lecture.builder()
                        .id(lectureOptionDto.getLectureId()).build())
                .registerBeginAt(lectureOptionDto.getRegisterBeginAt())
                .registerEndAt(lectureOptionDto.getRegisterEndAt())
                .lectureDatetime(lectureOptionDto.getLectureDatetime())
                .capacityLimit(lectureOptionDto.getCapacityLimit())
                .useYn(lectureOptionDto.getUseYn())
                .build();
    }

    @Override
    public List<LectureOptionDto> toDto(List<LectureOption> lectureOptions) {
        return lectureOptions.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<LectureOption> toEntity(List<LectureOptionDto> lectureOptionDtos) {
        return lectureOptionDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<LectureOptionDto> toDto(Optional<LectureOption> lectureOption) {
        if (lectureOption.isEmpty()) {
            return Optional.empty();
        }
        return lectureOption.map(this::toDto);
    }

    @Override
    public Optional<LectureOption> toEntity(Optional<LectureOptionDto> lectureOption) {
        if (lectureOption.isEmpty()) {
            return Optional.empty();
        }
        return lectureOption.map(this::toEntity);
    }
}
