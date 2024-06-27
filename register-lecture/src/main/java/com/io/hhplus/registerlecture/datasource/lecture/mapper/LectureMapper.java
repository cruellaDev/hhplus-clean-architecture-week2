package com.io.hhplus.registerlecture.datasource.lecture.mapper;

import com.io.hhplus.registerlecture.business.lecture.dto.LectureDto;
import com.io.hhplus.registerlecture.business.lecture.model.LectureStatus;
import com.io.hhplus.registerlecture.datasource.lecture.model.Lecture;
import com.io.hhplus.registerlecture.global.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LectureMapper implements EntityMapper<LectureDto, Lecture> {
    @Override
    public LectureDto toDto(Lecture lecture) {
        return LectureDto.builder()
                .id(lecture.getId())
                .name(lecture.getName())
                .status(LectureStatus.valueOf(lecture.getStatus()))
                .useYn(lecture.getUseYn())
                .createdAt(lecture.getAuditSection().getCreatedAt())
                .modifiedAt(lecture.getAuditSection().getModifiedAt())
                .build();
    }

    @Override
    public Lecture toEntity(LectureDto lectureDtoResponse) {
        return Lecture.builder()
                .id(lectureDtoResponse.getId())
                .name(lectureDtoResponse.getName())
                .status(lectureDtoResponse.getStatus().name())
                .useYn(lectureDtoResponse.getUseYn())
                .build();
    }

    @Override
    public List<LectureDto> toDto(List<Lecture> lectures) {
        return lectures.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Lecture> toEntity(List<LectureDto> lectureDtoResponse) {
        return lectureDtoResponse.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<LectureDto> toDto(Optional<Lecture> lecture) {
        if (lecture.isEmpty()) {
            return Optional.empty();
        }
        return lecture.map(this::toDto);
    }

    @Override
    public Optional<Lecture> toEntity(Optional<LectureDto> lectureResponse) {
        if (lectureResponse.isEmpty()) {
            return Optional.empty();
        }
        return lectureResponse.map(this::toEntity);
    }
}
