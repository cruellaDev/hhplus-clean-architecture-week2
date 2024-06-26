package com.io.hhplus.registerlecture.datasource.user.repository;

import com.io.hhplus.registerlecture.datasource.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
}
