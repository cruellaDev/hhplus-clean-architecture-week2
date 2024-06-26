package com.io.hhplus.registerlecture.datasource.userlecture.repository.impl;

import com.io.hhplus.registerlecture.business.userlecture.model.UserLectureRegisterHistory;
import com.io.hhplus.registerlecture.business.userlecture.repository.UserLectureRegisterHistoryRepository;
import com.io.hhplus.registerlecture.datasource.userlecture.mapper.UserLectureRegisterHistoryMapper;
import com.io.hhplus.registerlecture.datasource.userlecture.repository.UserLectureRegisterHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserLectureRegisterHistoryRepositoryImpl implements UserLectureRegisterHistoryRepository {

    private final UserLectureRegisterHistoryJpaRepository userLectureRegisterHistoryJpaRepository;
    private final UserLectureRegisterHistoryMapper userLectureRegisterHistoryMapper;

    @Override
    public UserLectureRegisterHistory save(UserLectureRegisterHistory userLectureRegisterHistory) {
        return userLectureRegisterHistoryMapper.toDto(userLectureRegisterHistoryJpaRepository.save(userLectureRegisterHistoryMapper.toEntity(userLectureRegisterHistory)));
    }
}
