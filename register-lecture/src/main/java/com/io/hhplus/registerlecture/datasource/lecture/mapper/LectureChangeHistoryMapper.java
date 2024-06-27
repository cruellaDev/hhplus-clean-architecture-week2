package com.io.hhplus.registerlecture.datasource.lecture.mapper;

import com.io.hhplus.registerlecture.business.lecture.model.LectureChangeHistory;
import com.io.hhplus.registerlecture.business.lecture.model.LectureStatus;
import com.io.hhplus.registerlecture.global.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LectureChangeHistoryMapper implements EntityMapper<LectureChangeHistory, com.io.hhplus.registerlecture.datasource.lecture.model.LectureChangeHistory> {
    @Override
    public LectureChangeHistory toDto(com.io.hhplus.registerlecture.datasource.lecture.model.LectureChangeHistory lectureChangeHistory) {
        return LectureChangeHistory.builder()
                .id(lectureChangeHistory.getId())
                .lectureId(lectureChangeHistory.getLectureId())
                .lectureName(lectureChangeHistory.getLectureName())
                .lectureOptionId(lectureChangeHistory.getLectureOptionId())
                .status(LectureStatus.valueOf(lectureChangeHistory.getStatus()))
                .registerBeginAt(lectureChangeHistory.getRegisterBeginAt())
                .registerEndAt(lectureChangeHistory.getRegisterEndAt())
                .lectureDatetime(lectureChangeHistory.getLectureDatetime())
                .capacityLimit(lectureChangeHistory.getCapacityLimit())
                .createdAt(lectureChangeHistory.getAuditSection().getCreatedAt())
                .modifiedAt(lectureChangeHistory.getAuditSection().getModifiedAt())
                .build();
    }

    @Override
    public com.io.hhplus.registerlecture.datasource.lecture.model.LectureChangeHistory toEntity(LectureChangeHistory lectureChangeHistory) {
        return com.io.hhplus.registerlecture.datasource.lecture.model.LectureChangeHistory.builder()
                .id(lectureChangeHistory.getId())
                .lectureId(lectureChangeHistory.getLectureId())
                .lectureName(lectureChangeHistory.getLectureName())
                .lectureOptionId(lectureChangeHistory.getLectureOptionId())
                .status(lectureChangeHistory.getStatus().name())
                .registerBeginAt(lectureChangeHistory.getRegisterBeginAt())
                .registerEndAt(lectureChangeHistory.getRegisterEndAt())
                .lectureDatetime(lectureChangeHistory.getLectureDatetime())
                .capacityLimit(lectureChangeHistory.getCapacityLimit())
                .build();
    }

    @Override
    public List<LectureChangeHistory> toDto(List<com.io.hhplus.registerlecture.datasource.lecture.model.LectureChangeHistory> lectureChangeHistories) {
        return lectureChangeHistories.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<com.io.hhplus.registerlecture.datasource.lecture.model.LectureChangeHistory> toEntity(List<LectureChangeHistory> lectureChangeHistories) {
        return lectureChangeHistories.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<LectureChangeHistory> toDto(Optional<com.io.hhplus.registerlecture.datasource.lecture.model.LectureChangeHistory> lectureChangeHistory) {
        if (lectureChangeHistory.isEmpty()) {
            return Optional.empty();
        }
        return lectureChangeHistory.map(this::toDto);
    }

    @Override
    public Optional<com.io.hhplus.registerlecture.datasource.lecture.model.LectureChangeHistory> toEntity(Optional<LectureChangeHistory> lectureChangeHistory) {
        if (lectureChangeHistory.isEmpty()) {
            return Optional.empty();
        }
        return lectureChangeHistory.map(this::toEntity);
    }
}
