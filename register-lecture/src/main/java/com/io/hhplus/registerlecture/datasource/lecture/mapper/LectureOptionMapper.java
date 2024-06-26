package com.io.hhplus.registerlecture.datasource.lecture.mapper;

import com.io.hhplus.registerlecture.business.lecture.model.LectureOption;
import com.io.hhplus.registerlecture.global.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LectureOptionMapper implements EntityMapper<LectureOption, com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption> {

    @Override
    public LectureOption toDto(com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption lectureOption) {
        return LectureOption.builder()
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
    public com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption toEntity(LectureOption lectureOption) {
        return com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption.builder()
                .id(lectureOption.getId())
                .lecture(com.io.hhplus.registerlecture.datasource.lecture.model.Lecture.builder()
                        .id(lectureOption.getLectureId()).build())
                .registerBeginAt(lectureOption.getRegisterBeginAt())
                .registerEndAt(lectureOption.getRegisterEndAt())
                .lectureDatetime(lectureOption.getLectureDatetime())
                .capacityLimit(lectureOption.getCapacityLimit())
                .useYn(lectureOption.getUseYn())
                .build();
    }

    @Override
    public List<LectureOption> toDto(List<com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption> lectureOptions) {
        return lectureOptions.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption> toEntity(List<LectureOption> lectureOptions) {
        return lectureOptions.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<LectureOption> toDto(Optional<com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption> lectureOption) {
        if (lectureOption.isEmpty()) {
            return Optional.empty();
        }
        return lectureOption.map(this::toDto);
    }

    @Override
    public Optional<com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption> toEntity(Optional<LectureOption> lectureOption) {
        if (lectureOption.isEmpty()) {
            return Optional.empty();
        }
        return lectureOption.map(this::toEntity);
    }
}
