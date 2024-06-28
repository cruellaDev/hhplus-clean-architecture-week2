package com.io.hhplus.registerlecture.datasource.userlecture.mapper;

import com.io.hhplus.registerlecture.business.userlecture.dto.UserLectureRegisterDto;
import com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegister;
import com.io.hhplus.registerlecture.global.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserLectureRegisterMapper implements EntityMapper<UserLectureRegisterDto, UserLectureRegister> {

    @Override
    public UserLectureRegisterDto toDto(UserLectureRegister userLectureRegister) {
        return UserLectureRegisterDto.builder()
                .id(userLectureRegister.getId())
                .userId(userLectureRegister.getUser().getId())
                .userName(userLectureRegister.getUser().getName())
                .lectureId(userLectureRegister.getLecture().getId())
                .lectureName(userLectureRegister.getLecture().getName())
                .lectureOptionId(userLectureRegister.getLectureOption().getId())
                .registerBeginAt(userLectureRegister.getLectureOption().getRegisterBeginAt())
                .registerEndAt(userLectureRegister.getLectureOption().getRegisterEndAt())
                .capacityLimit(userLectureRegister.getLectureOption().getCapacityLimit())
                .currentCapacity(userLectureRegister.getCurrentCapacity())
                .useYn(userLectureRegister.getUseYn())
                .createdAt(userLectureRegister.getAuditSection().getCreatedAt())
                .modifiedAt(userLectureRegister.getAuditSection().getModifiedAt())
                .build();
    }

    @Override
    public UserLectureRegister toEntity(UserLectureRegisterDto userLectureRegisterDto) {
        return UserLectureRegister.builder()
                .id(userLectureRegisterDto.getId())
                .user(com.io.hhplus.registerlecture.datasource.user.model.User.builder().id(userLectureRegisterDto.getUserId()).build())
                .lecture(com.io.hhplus.registerlecture.datasource.lecture.model.Lecture.builder().id(userLectureRegisterDto.getLectureId()).build())
                .lectureOption(com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption.builder().id(userLectureRegisterDto.getLectureOptionId()).build())
                .useYn(userLectureRegisterDto.getUseYn())
                .build();
    }

    @Override
    public List<UserLectureRegisterDto> toDto(List<UserLectureRegister> userLectureRegister) {
        return userLectureRegister.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<UserLectureRegister> toEntity(List<UserLectureRegisterDto> userLectureRegisterDto) {
        return userLectureRegisterDto.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<UserLectureRegisterDto> toDto(Optional<UserLectureRegister> userLectureRegister) {
        if (userLectureRegister.isEmpty()) {
            return Optional.empty();
        }
        return userLectureRegister.map(this::toDto);
    }

    @Override
    public Optional<UserLectureRegister> toEntity(Optional<UserLectureRegisterDto> userLectureRegisterResponse) {
        if (userLectureRegisterResponse.isEmpty()) {
            return Optional.empty();
        }
        return userLectureRegisterResponse.map(this::toEntity);
    }
}
