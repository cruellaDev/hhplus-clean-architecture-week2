package com.io.hhplus.registerlecture.datasource.userlecture.repository;

import com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegister;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLectureRegisterJpaRepository extends JpaRepository<UserLectureRegister, Long> {
    Optional<UserLectureRegister> findByUserIdAndLectureIdAndLectureOptionId(long userId, long lectureId, long lectureOptionId);
    long countByUserIdAndLectureIdAndLectureOptionIdAndUseYn(long userId, long lectureId, long lectureOptionId, String useYn);
    long countByLectureIdAndLectureOptionIdAndUseYn(long lectureId, long lectureOptionId, String useYn);
    List<UserLectureRegister> findAllByUserIdAndUseYn(long userId, String useYn);
}
