package com.io.hhplus.registerlecture.datasource.userlecture.mapper;

import com.io.hhplus.registerlecture.business.userlecture.model.UserLectureRegister;
import com.io.hhplus.registerlecture.global.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserLectureRegisterMapper implements EntityMapper<UserLectureRegister, com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegister> {

    @Override
    public UserLectureRegister toDto(com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegister userLectureRegister) {
        return UserLectureRegister.builder()
                .id(userLectureRegister.getId())
                .userId(userLectureRegister.getUser().getId())
                .lectureId(userLectureRegister.getLecture().getId())
                .lectureOptionId(userLectureRegister.getLectureOption().getId())
                .useYn(userLectureRegister.getUseYn())
                .build();
    }

    @Override
    public com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegister toEntity(UserLectureRegister userLectureRegister) {
        return com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegister.builder()
                .id(userLectureRegister.getId())
                .user(com.io.hhplus.registerlecture.datasource.user.model.User.builder().id(userLectureRegister.getUserId()).build())
                .lecture(com.io.hhplus.registerlecture.datasource.lecture.model.Lecture.builder().id(userLectureRegister.getLectureId()).build())
                .lectureOption(com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption.builder().id(userLectureRegister.getLectureOptionId()).build())
                .useYn(userLectureRegister.getUseYn())
                .build();
    }

    @Override
    public List<UserLectureRegister> toDto(List<com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegister> userLectureRegister) {
        return userLectureRegister.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegister> toEntity(List<UserLectureRegister> userLectureRegister) {
        return userLectureRegister.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<UserLectureRegister> toDto(Optional<com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegister> userLectureRegister) {
        if (userLectureRegister.isEmpty()) {
            return Optional.empty();
        }
        return userLectureRegister.map(this::toDto);
    }

    @Override
    public Optional<com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegister> toEntity(Optional<UserLectureRegister> userLectureRegisterResponse) {
        if (userLectureRegisterResponse.isEmpty()) {
            return Optional.empty();
        }
        return userLectureRegisterResponse.map(this::toEntity);
    }
}
