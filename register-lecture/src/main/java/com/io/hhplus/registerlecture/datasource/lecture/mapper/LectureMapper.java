package com.io.hhplus.registerlecture.datasource.lecture.mapper;

import com.io.hhplus.registerlecture.business.lecture.model.Lecture;
import com.io.hhplus.registerlecture.global.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LectureMapper implements EntityMapper<Lecture, com.io.hhplus.registerlecture.datasource.lecture.model.Lecture> {
    @Override
    public Lecture toDto(com.io.hhplus.registerlecture.datasource.lecture.model.Lecture lecture) {
        return Lecture.builder()
                .id(lecture.getId())
                .name(lecture.getName())
                .status(lecture.getStatus())
                .useYn(lecture.getUseYn())
                .createdAt(lecture.getAuditSection().getCreatedAt())
                .modifiedAt(lecture.getAuditSection().getModifiedAt())
                .build();
    }

    @Override
    public com.io.hhplus.registerlecture.datasource.lecture.model.Lecture toEntity(Lecture lectureResponse) {
        return com.io.hhplus.registerlecture.datasource.lecture.model.Lecture.builder()
                .id(lectureResponse.getId())
                .name(lectureResponse.getName())
                .status(lectureResponse.getStatus())
                .useYn(lectureResponse.getUseYn())
                .build();
    }

    @Override
    public List<Lecture> toDto(List<com.io.hhplus.registerlecture.datasource.lecture.model.Lecture> lectures) {
        return lectures.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<com.io.hhplus.registerlecture.datasource.lecture.model.Lecture> toEntity(List<Lecture> lectureResponses) {
        return lectureResponses.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<Lecture> toDto(Optional<com.io.hhplus.registerlecture.datasource.lecture.model.Lecture> lecture) {
        if (lecture.isEmpty()) {
            return Optional.empty();
        }
        return lecture.map(this::toDto);
    }

    @Override
    public Optional<com.io.hhplus.registerlecture.datasource.lecture.model.Lecture> toEntity(Optional<Lecture> lectureResponse) {
        if (lectureResponse.isEmpty()) {
            return Optional.empty();
        }
        return lectureResponse.map(this::toEntity);
    }
}
