package com.io.hhplus.registerlecture.datasource.userlecture.repository;

import com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLectureRegisterJpaRepository extends JpaRepository<UserLectureRegister, Long> {
}
