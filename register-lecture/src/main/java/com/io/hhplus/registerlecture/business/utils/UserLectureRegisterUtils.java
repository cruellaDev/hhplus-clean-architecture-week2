package com.io.hhplus.registerlecture.business.utils;

import com.io.hhplus.registerlecture.business.lecture.dto.LectureDto;
import com.io.hhplus.registerlecture.business.lecture.dto.LectureOptionDto;
import com.io.hhplus.registerlecture.business.user.dto.UserDto;
import com.io.hhplus.registerlecture.business.userlecture.dto.UserLectureRegisterDto;
import com.io.hhplus.registerlecture.business.userlecture.dto.UserLectureRegisterHistoryDto;
import com.io.hhplus.registerlecture.business.userlecture.model.UserLectureRegisterType;
import com.io.hhplus.registerlecture.business.userlecture.repository.UserLectureRegisterHistoryRepository;
import com.io.hhplus.registerlecture.business.userlecture.repository.UserLectureRegisterRepository;
import com.io.hhplus.registerlecture.global.processor.ProcessCode;
import com.io.hhplus.registerlecture.global.processor.ProcessResultDto;
import com.io.hhplus.registerlecture.global.processor.Processor;
import com.io.hhplus.registerlecture.global.reader.Reader;
import com.io.hhplus.registerlecture.global.utils.CommonUtils;
import com.io.hhplus.registerlecture.global.utils.GlobalConstants;
import com.io.hhplus.registerlecture.global.writer.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserLectureRegisterUtils implements Reader<UserLectureRegisterDto>, Processor<UserLectureRegisterDto>, Writer<UserLectureRegisterDto, UserLectureRegisterDto> {

    private final UserLectureRegisterRepository userLectureRegisterRepository;
    private final UserLectureRegisterHistoryRepository userLectureRegisterHistoryRepository;

    @Override
    public UserLectureRegisterDto getOneByPrimaryId(Long primaryId) {
        return userLectureRegisterRepository.findById(primaryId).orElseGet(this::empty);
    }

    @Override
    public ProcessResultDto validateAvailableDataByPrimaryId(Long primaryId) {
        // 사용 안 함
        return CommonUtils.createProcessResult(ProcessCode.FAIL, "잘못된 접근입니다.");
    }

    @Override
    public ProcessResultDto validateBeforePersist(UserLectureRegisterDto userLectureRegisterDto) {
        if (CommonUtils.isEmpty(userLectureRegisterDto)) {
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "입력할 데이터가 없습니다.");
        }
        if (CommonUtils.isEmpty(userLectureRegisterDto.getUserId())) {
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "사용자ID를 입력해 주십시오");
        }
        if (CommonUtils.isEmpty(userLectureRegisterDto.getLectureId())) {
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "강의ID를 입력해 주십시오");
        }
        if (CommonUtils.isEmpty(userLectureRegisterDto.getLectureOptionId())) {
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "강의옵션ID를 입력해 주십시오");
        }
        if (CommonUtils.isBlank(userLectureRegisterDto.getUseYn())) {
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "신청여부를 입력해 주십시오");
        }
        if (!CommonUtils.hasYN(userLectureRegisterDto.getUseYn())) {
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "신청여부의 값을 확인해 주십시오");
        }
        return CommonUtils.createProcessResult(ProcessCode.VALID, null);
    }

    @Override
    public ProcessResultDto validateAfterPersist(UserLectureRegisterDto userLectureRegisterDto) {
        String detailMessage = null;
        if (CommonUtils.isEmpty(userLectureRegisterDto) || CommonUtils.isNotFoundData(userLectureRegisterDto.getId())) {
            detailMessage = "저장 중 오류가 발생하였습니다.";
            return CommonUtils.createProcessResult(ProcessCode.FAIL, detailMessage);
        }
        return CommonUtils.createProcessResult(ProcessCode.SUCCESS, detailMessage);
    }

    public UserLectureRegisterDto getOneByUserIdAndLectureIdAndLectureOptionId(Long userId, Long lectureId, Long lectureOptionId) {
        return userLectureRegisterRepository.findByUserIdAndLectureIdAndLectureOptionId(userId, lectureId, lectureOptionId).orElseGet(this::empty);
    }

    @Override
    public UserLectureRegisterDto write(UserLectureRegisterDto userLectureRegisterDto) {
        try {
            return userLectureRegisterRepository.save(userLectureRegisterDto);
        } catch (Exception e) {
            return this.empty();
        }
    }

    @Override
    public UserLectureRegisterDto empty() {
        return UserLectureRegisterDto.builder().id(GlobalConstants.NOT_FOUND_ID).build();
    }

    public UserLectureRegisterHistoryDto write(UserLectureRegisterHistoryDto userLectureRegisterHistoryDto) {
        return userLectureRegisterHistoryRepository.save(userLectureRegisterHistoryDto);
    }

    public UserLectureRegisterHistoryDto emptyHistory() {
        return UserLectureRegisterHistoryDto.builder().id(GlobalConstants.NOT_FOUND_ID).build();
    }

    public UserLectureRegisterDto set(UserDto userDto, LectureDto lectureDto, LectureOptionDto lectureOptionDto, UserLectureRegisterType type) {
        return UserLectureRegisterDto.builder()
                .userId(userDto.getId())
                .lectureId(lectureDto.getId())
                .lectureName(lectureDto.getName())
                .lectureOptionId(lectureOptionDto.getId())
                .registerBeginAt(lectureOptionDto.getRegisterBeginAt())
                .registerEndAt(lectureOptionDto.getRegisterEndAt())
                .lectureDatetime(lectureOptionDto.getLectureDatetime())
                .capacityLimit(lectureOptionDto.getCapacityLimit())
                .useYn(UserLectureRegisterType.APPLY.equals(type) ? "Y" : "N")
                .build();
    }

    public UserLectureRegisterHistoryDto setHistory(UserLectureRegisterDto userLectureRegisterDto, UserLectureRegisterType type) {
        return UserLectureRegisterHistoryDto.builder()
                .userLectureRegisterId(userLectureRegisterDto.getId())
                .userId(userLectureRegisterDto.getUserId())
                .lectureId(userLectureRegisterDto.getLectureId())
                .lectureOptionId(userLectureRegisterDto.getLectureOptionId())
                .type(type)
                .build();
    }

    public long getUserLectureRegisterCount(Long lectureId, Long lectureOptionId) {
        return userLectureRegisterRepository.countByLectureIdAndLectureOptionIdAndUseYn(lectureId, lectureOptionId, "Y");
    }

    public boolean isAlreadyApplied(Long userId, Long lectureId, Long lectureOptionId) {
        return userLectureRegisterRepository.countByUserIdAndLectureIdAndLectureOptionIdWithUseYn(userId, lectureId, lectureOptionId, "Y") == 1;
    }

    public List<UserLectureRegisterDto> getAllByUserId(Long userId) {
        return userLectureRegisterRepository.findAllByUserIdAndUseYn(userId, "Y");
    }
}
