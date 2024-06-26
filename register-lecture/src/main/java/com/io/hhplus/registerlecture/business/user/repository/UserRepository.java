package com.io.hhplus.registerlecture.business.user.repository;

import com.io.hhplus.registerlecture.business.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<User> findById(long useId);
    User save(User user);
}
