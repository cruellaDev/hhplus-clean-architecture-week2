package com.io.hhplus.registerlecture.datasource.userlecture.mapper;

import com.io.hhplus.registerlecture.business.userlecture.dto.UserLectureRegisterHistoryDto;
import com.io.hhplus.registerlecture.business.userlecture.model.UserLectureRegisterType;
import com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegisterHistory;
import com.io.hhplus.registerlecture.global.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserLectureRegisterHistoryMapper implements EntityMapper<UserLectureRegisterHistoryDto, UserLectureRegisterHistory> {
    @Override
    public UserLectureRegisterHistoryDto toDto(UserLectureRegisterHistory userLectureRegisterHistory) {
        return UserLectureRegisterHistoryDto.builder()
                .id(userLectureRegisterHistory.getId())
                .userLectureRegisterId(userLectureRegisterHistory.getUserLectureRegisterId())
                .userId(userLectureRegisterHistory.getUserId())
                .lectureId(userLectureRegisterHistory.getLectureId())
                .lectureOptionId(userLectureRegisterHistory.getLectureOptionId())
                .lectureDatetime(userLectureRegisterHistory.getLectureDatetime())
                .type(UserLectureRegisterType.valueOf(userLectureRegisterHistory.getType()))
                .createdAt(userLectureRegisterHistory.getAuditSection().getCreatedAt())
                .modifiedAt(userLectureRegisterHistory.getAuditSection().getModifiedAt())
                .build();
    }

    @Override
    public UserLectureRegisterHistory toEntity(UserLectureRegisterHistoryDto userLectureRegisterHistoryDto) {
        return UserLectureRegisterHistory.builder()
                .id(userLectureRegisterHistoryDto.getId())
                .userLectureRegisterId(userLectureRegisterHistoryDto.getUserLectureRegisterId())
                .userId(userLectureRegisterHistoryDto.getUserId())
                .lectureId(userLectureRegisterHistoryDto.getLectureId())
                .lectureOptionId(userLectureRegisterHistoryDto.getLectureOptionId())
                .lectureDatetime(userLectureRegisterHistoryDto.getLectureDatetime())
                .type(userLectureRegisterHistoryDto.getType().name())
                .build();
    }

    @Override
    public List<UserLectureRegisterHistoryDto> toDto(List<UserLectureRegisterHistory> userLectureRegisterHistories) {
        return userLectureRegisterHistories.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<UserLectureRegisterHistory> toEntity(List<UserLectureRegisterHistoryDto> userLectureRegisterHistories) {
        return userLectureRegisterHistories.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<UserLectureRegisterHistoryDto> toDto(Optional<UserLectureRegisterHistory> userLectureRegisterHistory) {
        if (userLectureRegisterHistory.isEmpty()) {
            return Optional.empty();
        }
        return userLectureRegisterHistory.map(this::toDto);
    }

    @Override
    public Optional<UserLectureRegisterHistory> toEntity(Optional<UserLectureRegisterHistoryDto> userLectureRegisterHistory) {
        if (userLectureRegisterHistory.isEmpty()) {
            return Optional.empty();
        }
        return userLectureRegisterHistory.map(this::toEntity);
    }
}
