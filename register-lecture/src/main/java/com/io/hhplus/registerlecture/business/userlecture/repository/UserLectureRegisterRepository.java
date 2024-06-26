package com.io.hhplus.registerlecture.business.userlecture.repository;

import com.io.hhplus.registerlecture.business.userlecture.model.UserLectureRegister;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLectureRegisterRepository {
    Optional<UserLectureRegister> findByUserIdAndLectureIdAndLectureOptionId(long userId, long lectureId, long lectureOptionId);
}
