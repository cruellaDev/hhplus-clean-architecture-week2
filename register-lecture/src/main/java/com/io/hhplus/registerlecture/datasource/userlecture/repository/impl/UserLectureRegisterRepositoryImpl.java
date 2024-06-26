package com.io.hhplus.registerlecture.datasource.userlecture.repository.impl;

import com.io.hhplus.registerlecture.business.userlecture.model.UserLectureRegister;
import com.io.hhplus.registerlecture.business.userlecture.repository.UserLectureRegisterRepository;
import com.io.hhplus.registerlecture.datasource.userlecture.repository.UserLectureRegisterJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserLectureRegisterRepositoryImpl implements UserLectureRegisterRepository {

    private final UserLectureRegisterJpaRepository userLectureRegisterJpaRepository;

    @Override
    public Optional<UserLectureRegister> findByUserIdAndLectureIdAndLectureOptionId(long userId, long lectureId, long lectureOptionId) {
        return Optional.empty();
    }
}
