package com.io.hhplus.registerlecture.datasource.userlecture.repository.impl;

import com.io.hhplus.registerlecture.business.userlecture.model.UserLectureRegister;
import com.io.hhplus.registerlecture.business.userlecture.repository.UserLectureRegisterRepository;
import com.io.hhplus.registerlecture.datasource.userlecture.mapper.UserLectureRegisterMapper;
import com.io.hhplus.registerlecture.datasource.userlecture.repository.UserLectureRegisterJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserLectureRegisterRepositoryImpl implements UserLectureRegisterRepository {

    private final UserLectureRegisterJpaRepository userLectureRegisterJpaRepository;
    private final UserLectureRegisterMapper userLectureRegisterMapper;

    @Override
    public Optional<UserLectureRegister> findByUserIdAndLectureIdAndLectureOptionId(long userId, long lectureId, long lectureOptionId) {
        return userLectureRegisterMapper.toDto(userLectureRegisterJpaRepository.findByUserIdAndLectureIdAndLectureOptionId(userId, lectureId, lectureOptionId));
    }

    @Override
    public long countByUserIdAndLectureIdAndLectureOptionIdWithUseYn(long userId, long lectureId, long lectureOptionId, String useYn) {
        return userLectureRegisterJpaRepository.countByUserIdAndLectureIdAndLectureOptionIdAndUseYn(userId, lectureId, lectureOptionId, useYn);
    }

    @Override
    public long countByLectureIdAndLectureOptionIdAndUseYn(long lectureId, long lectureOptionId, String useYn) {
        return userLectureRegisterJpaRepository.countByLectureIdAndLectureOptionIdAndUseYn(lectureId, lectureOptionId, useYn);
    }

    @Override
    public UserLectureRegister save(UserLectureRegister userLectureRegister) {
        return userLectureRegisterMapper.toDto(userLectureRegisterJpaRepository.save(userLectureRegisterMapper.toEntity(userLectureRegister)));
    }

    @Override
    public Optional<UserLectureRegister> findById(long userLectureRegisterId) {
        return userLectureRegisterMapper.toDto(userLectureRegisterJpaRepository.findById(userLectureRegisterId));
    }

    @Override
    public List<UserLectureRegister> findAllByUserIdAndUseYn(long userId, String useYn) {
        return userLectureRegisterMapper.toDto(userLectureRegisterJpaRepository.findAllByUserIdAndUseYn(userId, useYn));
    }
}
