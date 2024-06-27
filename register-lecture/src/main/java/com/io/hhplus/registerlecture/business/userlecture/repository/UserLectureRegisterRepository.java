package com.io.hhplus.registerlecture.business.userlecture.repository;

import com.io.hhplus.registerlecture.business.userlecture.dto.UserLectureRegisterDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLectureRegisterRepository {
    Optional<UserLectureRegisterDto> findByUserIdAndLectureIdAndLectureOptionId(long userId, long lectureId, long lectureOptionId);
    long countByUserIdAndLectureIdAndLectureOptionIdWithUseYn(long userId, long lectureId, long lectureOptionId, String useYn);
    long countByLectureIdAndLectureOptionIdAndUseYn(long lectureId, long lectureOptionId, String useYn);
    UserLectureRegisterDto save(UserLectureRegisterDto userLectureRegisterDto);
    Optional<UserLectureRegisterDto> findById(long userLectureRegisterId);
    List<UserLectureRegisterDto> findAllByUserIdAndUseYn(long useId, String useYn);
}
