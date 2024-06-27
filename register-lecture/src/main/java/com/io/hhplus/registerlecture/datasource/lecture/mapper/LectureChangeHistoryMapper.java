package com.io.hhplus.registerlecture.datasource.lecture.mapper;

import com.io.hhplus.registerlecture.business.lecture.dto.LectureChangeHistoryDto;
import com.io.hhplus.registerlecture.business.lecture.model.LectureStatus;
import com.io.hhplus.registerlecture.datasource.lecture.model.LectureChangeHistory;
import com.io.hhplus.registerlecture.global.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LectureChangeHistoryMapper implements EntityMapper<LectureChangeHistoryDto, LectureChangeHistory> {
    @Override
    public LectureChangeHistoryDto toDto(LectureChangeHistory lectureChangeHistory) {
        return LectureChangeHistoryDto.builder()
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
    public LectureChangeHistory toEntity(LectureChangeHistoryDto lectureChangeHistoryDto) {
        return LectureChangeHistory.builder()
                .id(lectureChangeHistoryDto.getId())
                .lectureId(lectureChangeHistoryDto.getLectureId())
                .lectureName(lectureChangeHistoryDto.getLectureName())
                .lectureOptionId(lectureChangeHistoryDto.getLectureOptionId())
                .status(lectureChangeHistoryDto.getStatus().name())
                .registerBeginAt(lectureChangeHistoryDto.getRegisterBeginAt())
                .registerEndAt(lectureChangeHistoryDto.getRegisterEndAt())
                .lectureDatetime(lectureChangeHistoryDto.getLectureDatetime())
                .capacityLimit(lectureChangeHistoryDto.getCapacityLimit())
                .build();
    }

    @Override
    public List<LectureChangeHistoryDto> toDto(List<LectureChangeHistory> lectureChangeHistories) {
        return lectureChangeHistories.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<LectureChangeHistory> toEntity(List<LectureChangeHistoryDto> lectureChangeHistories) {
        return lectureChangeHistories.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<LectureChangeHistoryDto> toDto(Optional<LectureChangeHistory> lectureChangeHistory) {
        if (lectureChangeHistory.isEmpty()) {
            return Optional.empty();
        }
        return lectureChangeHistory.map(this::toDto);
    }

    @Override
    public Optional<LectureChangeHistory> toEntity(Optional<LectureChangeHistoryDto> lectureChangeHistory) {
        if (lectureChangeHistory.isEmpty()) {
            return Optional.empty();
        }
        return lectureChangeHistory.map(this::toEntity);
    }
}
