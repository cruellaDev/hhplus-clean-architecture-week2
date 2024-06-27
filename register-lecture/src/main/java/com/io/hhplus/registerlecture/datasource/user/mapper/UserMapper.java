package com.io.hhplus.registerlecture.datasource.user.mapper;

import com.io.hhplus.registerlecture.business.user.dto.UserDto;
import com.io.hhplus.registerlecture.datasource.user.model.User;
import com.io.hhplus.registerlecture.global.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper implements EntityMapper<UserDto, User> {
    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .useYn(user.getUseYn())
                .createdAt(user.getAuditSection().getCreatedAt())
                .modifiedAt(user.getAuditSection().getModifiedAt())
                .build();
    }

    @Override
    public User toEntity(UserDto userDtoResponse) {
        return User.builder()
                .id(userDtoResponse.getId())
                .name(userDtoResponse.getName())
                .useYn(userDtoResponse.getUseYn())
                .build();
    }

    @Override
    public List<UserDto> toDto(List<User> users) {
        return users.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<User> toEntity(List<UserDto> userDtoResponse) {
        return userDtoResponse.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> toDto(Optional<User> user) {
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return user.map(this::toDto);
    }

    @Override
    public Optional<User> toEntity(Optional<UserDto> userResponse) {
        if (userResponse.isEmpty()) {
            return Optional.empty();
        }
        return userResponse.map(this::toEntity);
    }
}
