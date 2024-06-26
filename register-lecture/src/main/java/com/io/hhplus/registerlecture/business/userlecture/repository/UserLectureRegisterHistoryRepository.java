package com.io.hhplus.registerlecture.business.userlecture.repository;

import com.io.hhplus.registerlecture.business.userlecture.model.UserLectureRegisterHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLectureRegisterHistoryRepository {
    UserLectureRegisterHistory save(UserLectureRegisterHistory userLectureRegisterHistoryRequest);
}
