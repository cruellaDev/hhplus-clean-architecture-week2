package com.io.hhplus.registerlecture.business.userlecture.service;

import com.io.hhplus.registerlecture.business.lecture.dto.LectureDto;
import com.io.hhplus.registerlecture.business.lecture.dto.LectureOptionDto;
import com.io.hhplus.registerlecture.business.user.dto.UserDto;
import com.io.hhplus.registerlecture.business.userlecture.dto.UserLectureRegisterDto;
import com.io.hhplus.registerlecture.business.userlecture.dto.UserLectureRegisterHistoryDto;
import com.io.hhplus.registerlecture.business.userlecture.model.UserLectureRegisterType;
import com.io.hhplus.registerlecture.business.utils.LectureUtils;
import com.io.hhplus.registerlecture.business.utils.UserLectureRegisterUtils;
import com.io.hhplus.registerlecture.business.utils.UserUtils;
import com.io.hhplus.registerlecture.global.processor.ProcessCode;
import com.io.hhplus.registerlecture.global.processor.ProcessResultDto;
import com.io.hhplus.registerlecture.presentation.model.ListResponse;
import com.io.hhplus.registerlecture.presentation.model.SingleResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserLectureRegisterService {
    private final UserUtils userUtils;
    private final LectureUtils lectureUtils;
    private final UserLectureRegisterUtils userLectureRegisterUtils;

    /**
     * 특강 신청 하기
     */
    @Transactional
    public SingleResponse<UserLectureRegisterDto> register(Long userId, Long lectureId, Long lectureOptionId) {
        ProcessResultDto processResultDto;
        UserLectureRegisterDto tempUserLectureRegisterDto =  UserLectureRegisterDto.builder()
                .userId(userId)
                .lectureId(lectureId)
                .lectureOptionId(lectureOptionId)
                .build();

        // 사용자 검증
        processResultDto = userUtils.validateAvailableDataByPrimaryId(userId);
        if (isStatusNotOk(processResultDto.getProcessCode())) {
            return new SingleResponse<>(false, tempUserLectureRegisterDto, processResultDto.getProcessCode().name(), processResultDto.getDetailMessage());
        }

        // 강의 검증
        processResultDto = lectureUtils.validateAvailableDataByPrimaryIdAndOptionId(lectureId, lectureOptionId);
        if (isStatusNotOk(processResultDto.getProcessCode())) {
            return new SingleResponse<>(
                    false,
                    tempUserLectureRegisterDto,
                    processResultDto.getProcessCode().name(),
                    processResultDto.getDetailMessage());
        }

        // 강의 옵션 검증
        processResultDto = lectureUtils.validateAvailableDataByPrimaryIdAndOptionId(lectureId, lectureOptionId);
        if (isStatusNotOk(processResultDto.getProcessCode())) {
            return new SingleResponse<>(
                    false,
                    tempUserLectureRegisterDto,
                    processResultDto.getProcessCode().name(),
                    processResultDto.getDetailMessage());
        }
        // 사용자 조회
        UserDto userDto = userUtils.getOneByPrimaryId(userId);
        // 강의 조회
        LectureDto lectureDto = lectureUtils.getOneByPrimaryId(lectureId);
        // 강의 옵션 조회
        LectureOptionDto lectureOptionDto = lectureUtils.getOneByPrimaryIdAndOptionId(lectureId, lectureOptionId);
        // 사용자 강의 등록 세팅
        UserLectureRegisterDto userLectureRegisterDto = userLectureRegisterUtils.set(userDto, lectureDto, lectureOptionDto, UserLectureRegisterType.APPLY);
        // 사용자 강의 등록 이력 저장
        UserLectureRegisterHistoryDto userLectureRegisterHistoryDto = userLectureRegisterUtils.setHistory(userLectureRegisterDto, UserLectureRegisterType.APPLY);
        // 사용자 강의 저장
        userLectureRegisterUtils.write(userLectureRegisterDto);
        // 사용자 강의 이력 저장
        userLectureRegisterUtils.write(userLectureRegisterHistoryDto);

        return new SingleResponse<>(true, userLectureRegisterUtils.getOneByUserIdAndLectureIdAndLectureOptionId(userId, lectureId, lectureOptionId), ProcessCode.SUCCESS.name(), null);
    }

    /**
     * 모든 특강 목록 조회하기
     */
    public ListResponse<LectureOptionDto> getAvailableLecture() {
        return new ListResponse<>(true, lectureUtils.getAllAvailableLecture(), ProcessCode.SUCCESS.name(), null);
    }

    /**
     * 신청한 특강 목록 조회 하기
     */
    public ListResponse<UserLectureRegisterDto> getRegisteredLecture(Long userId) {
        return new ListResponse<>(true, userLectureRegisterUtils.getAllByUserId(userId), ProcessCode.SUCCESS.name(), null);
    }

    private boolean isStatusNotOk(ProcessCode processCode) {
        return !(ProcessCode.VALID.equals(processCode)
                || ProcessCode.SUCCESS.equals(processCode));
    }
}
