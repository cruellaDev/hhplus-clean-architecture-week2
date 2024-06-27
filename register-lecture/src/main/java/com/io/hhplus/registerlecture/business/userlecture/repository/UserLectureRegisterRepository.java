package com.io.hhplus.registerlecture.business.userlecture.repository;

import com.io.hhplus.registerlecture.business.userlecture.model.UserLectureRegister;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLectureRegisterRepository {
    Optional<UserLectureRegister> findByUserIdAndLectureIdAndLectureOptionId(long userId, long lectureId, long lectureOptionId);
    long countByUserIdAndLectureIdAndLectureOptionIdWithUseYn(long userId, long lectureId, long lectureOptionId, String useYn);
    long countByLectureIdAndLectureOptionIdAndUseYn(long lectureId, long lectureOptionId, String useYn);
    UserLectureRegister save(UserLectureRegister userLectureRegister);
    Optional<UserLectureRegister> findById(long userLectureRegisterId);
    List<UserLectureRegister> findAllByUserIdAndUseYn(long useId, String useYn);
}
