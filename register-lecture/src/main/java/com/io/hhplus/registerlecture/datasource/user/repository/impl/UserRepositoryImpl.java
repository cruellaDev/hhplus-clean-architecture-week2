package com.io.hhplus.registerlecture.datasource.user.repository.impl;

import com.io.hhplus.registerlecture.business.user.dto.UserDto;
import com.io.hhplus.registerlecture.business.user.repository.UserRepository;
import com.io.hhplus.registerlecture.datasource.user.mapper.UserMapper;
import com.io.hhplus.registerlecture.datasource.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<UserDto> findById(long useId) {
        return userMapper.toDto(userJpaRepository.findById(useId));
    }

    @Override
    public UserDto save(UserDto userDto) {
        return userMapper.toDto(userJpaRepository.save(userMapper.toEntity(userDto)));
    }
}
