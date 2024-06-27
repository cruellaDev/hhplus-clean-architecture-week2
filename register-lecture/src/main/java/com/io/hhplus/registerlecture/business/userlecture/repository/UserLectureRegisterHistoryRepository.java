package com.io.hhplus.registerlecture.business.userlecture.repository;

import com.io.hhplus.registerlecture.business.userlecture.dto.UserLectureRegisterHistoryDto;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLectureRegisterHistoryRepository {
    UserLectureRegisterHistoryDto save(UserLectureRegisterHistoryDto userLectureRegisterHistoryDtoRequest);
}
