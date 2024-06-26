package com.io.hhplus.registerlecture.datasource.user.mapper;

import com.io.hhplus.registerlecture.business.user.model.User;
import com.io.hhplus.registerlecture.global.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper implements EntityMapper<User, com.io.hhplus.registerlecture.datasource.user.model.User> {
    @Override
    public User toDto(com.io.hhplus.registerlecture.datasource.user.model.User user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .useYn(user.getUseYn())
                .createdAt(user.getAuditSection().getCreatedAt())
                .modifiedAt(user.getAuditSection().getModifiedAt())
                .build();
    }

    @Override
    public com.io.hhplus.registerlecture.datasource.user.model.User toEntity(User userResponse) {
        return com.io.hhplus.registerlecture.datasource.user.model.User.builder()
                .id(userResponse.getId())
                .name(userResponse.getName())
                .useYn(userResponse.getUseYn())
                .build();
    }

    @Override
    public List<User> toDto(List<com.io.hhplus.registerlecture.datasource.user.model.User> users) {
        return users.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<com.io.hhplus.registerlecture.datasource.user.model.User> toEntity(List<User> userResponses) {
        return userResponses.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<User> toDto(Optional<com.io.hhplus.registerlecture.datasource.user.model.User> user) {
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return user.map(this::toDto);
    }

    @Override
    public Optional<com.io.hhplus.registerlecture.datasource.user.model.User> toEntity(Optional<User> userResponse) {
        if (userResponse.isEmpty()) {
            return Optional.empty();
        }
        return userResponse.map(this::toEntity);
    }
}
