package com.io.hhplus.registerlecture.business.user.repository;

import com.io.hhplus.registerlecture.business.user.dto.UserDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<UserDto> findById(long useId);
    UserDto save(UserDto userDto);
}
