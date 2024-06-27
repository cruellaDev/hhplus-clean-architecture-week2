package com.io.hhplus.registerlecture.datasource.userlecture.mapper;

import com.io.hhplus.registerlecture.business.userlecture.model.UserLectureRegisterHistory;
import com.io.hhplus.registerlecture.business.userlecture.model.UserLectureRegisterType;
import com.io.hhplus.registerlecture.global.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserLectureRegisterHistoryMapper implements EntityMapper<UserLectureRegisterHistory, com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegisterHistory> {
    @Override
    public UserLectureRegisterHistory toDto(com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegisterHistory userLectureRegisterHistory) {
        return UserLectureRegisterHistory.builder()
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
    public com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegisterHistory toEntity(UserLectureRegisterHistory userLectureRegisterHistory) {
        return com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegisterHistory.builder()
                .id(userLectureRegisterHistory.getId())
                .userLectureRegisterId(userLectureRegisterHistory.getUserLectureRegisterId())
                .userId(userLectureRegisterHistory.getUserId())
                .lectureId(userLectureRegisterHistory.getLectureId())
                .lectureOptionId(userLectureRegisterHistory.getLectureOptionId())
                .lectureDatetime(userLectureRegisterHistory.getLectureDatetime())
                .type(userLectureRegisterHistory.getType().name())
                .build();
    }

    @Override
    public List<UserLectureRegisterHistory> toDto(List<com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegisterHistory> userLectureRegisterHistories) {
        return userLectureRegisterHistories.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegisterHistory> toEntity(List<UserLectureRegisterHistory> userLectureRegisterHistories) {
        return userLectureRegisterHistories.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<UserLectureRegisterHistory> toDto(Optional<com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegisterHistory> userLectureRegisterHistory) {
        if (userLectureRegisterHistory.isEmpty()) {
            return Optional.empty();
        }
        return userLectureRegisterHistory.map(this::toDto);
    }

    @Override
    public Optional<com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegisterHistory> toEntity(Optional<UserLectureRegisterHistory> userLectureRegisterHistory) {
        if (userLectureRegisterHistory.isEmpty()) {
            return Optional.empty();
        }
        return userLectureRegisterHistory.map(this::toEntity);
    }
}
