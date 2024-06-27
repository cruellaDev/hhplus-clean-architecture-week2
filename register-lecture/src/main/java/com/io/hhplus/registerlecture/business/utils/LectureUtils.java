package com.io.hhplus.registerlecture.business.utils;

import com.io.hhplus.registerlecture.business.lecture.dto.LectureDto;
import com.io.hhplus.registerlecture.business.lecture.dto.LectureOptionDto;
import com.io.hhplus.registerlecture.business.lecture.model.LectureStatus;
import com.io.hhplus.registerlecture.business.lecture.dto.LectureChangeHistoryDto;
import com.io.hhplus.registerlecture.business.lecture.repository.LectureChangeHistoryRepository;
import com.io.hhplus.registerlecture.business.lecture.repository.LectureOptionRepository;
import com.io.hhplus.registerlecture.business.lecture.repository.LectureRepository;
import com.io.hhplus.registerlecture.global.processor.ProcessCode;
import com.io.hhplus.registerlecture.global.processor.ProcessResultDto;
import com.io.hhplus.registerlecture.global.processor.Processor;
import com.io.hhplus.registerlecture.global.reader.Reader;
import com.io.hhplus.registerlecture.global.utils.CommonUtils;
import com.io.hhplus.registerlecture.global.utils.DateUtils;
import com.io.hhplus.registerlecture.global.utils.GlobalConstants;
import com.io.hhplus.registerlecture.global.writer.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.StringJoiner;

@Component
@RequiredArgsConstructor
public class LectureUtils implements Reader<LectureDto>, Processor<LectureDto>, Writer<LectureDto, LectureDto> {

    private final LectureRepository lectureRepository;
    private final LectureOptionRepository lectureOptionRepository;
    private final LectureChangeHistoryRepository lectureChangeHistoryRepository;

    private final UserLectureUtils userLectureUtils;

    @Override
    public LectureDto getOneByPrimaryId(Long primaryId) {
        try {
            return lectureRepository.findById(primaryId).orElseGet(this::empty);
        } catch (Exception e) {
            return this.empty();
        }
    }

    @Override
    public LectureDto write(LectureDto lectureDto) {
        try {
            return lectureRepository.save(lectureDto);
        } catch (Exception e) {
            return this.empty();
        }
    }

    @Override
    public ProcessResultDto validateAvailableDataByPrimaryId(Long primaryId) {
        String detailMessage = null;
        if (CommonUtils.isEmpty(primaryId)) {
            detailMessage = "강의ID를 입력해주십시오.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, detailMessage);
        }
        if (!CommonUtils.isPositiveLong(primaryId)) {
            detailMessage = "강의ID는 1 이상의 정수여야 합니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, detailMessage);
        }

        // read data
        LectureDto lectureDto = this.getOneByPrimaryId(primaryId);

        // validate if available data exists
        if (CommonUtils.isNotFoundData(lectureDto.getId()) || !CommonUtils.isUsable(lectureDto.getUseYn())) {
            detailMessage = "존재하지 않는 강의입니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        if (!LectureStatus.OPEN.equals(lectureDto.getStatus())) {
            detailMessage = "해당 강의는 수강이 가능한 상태가 아닙니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }

        return CommonUtils.createProcessResult(ProcessCode.VALID, detailMessage);
    }

    @Override
    public ProcessResultDto validateBeforePersist(LectureDto lectureDto) {
        // if arguments empty
        if (CommonUtils.isEmpty(lectureDto)) {
            return CommonUtils.createProcessResult(ProcessCode.EMPTY, "입력할 데이터가 없습니다.");
        }
        // check available if positive id exists
        if (CommonUtils.isPositiveLong(lectureDto.getId())) {
            LectureDto existsLectureDto = this.getOneByPrimaryId(lectureDto.getId());
            if (CommonUtils.isNotFoundData(existsLectureDto.getId())) {
                return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, "존재하지 않는 강의입니다.");
            }
        }

        StringJoiner detailMessage = new StringJoiner(",");
        // validate arguments in detail
        if (!CommonUtils.isEmpty(lectureDto.getId()) && !CommonUtils.isPositiveLong(lectureDto.getId())) {
            detailMessage.add("강의ID를 확인해 주십시오");
        }
        if (CommonUtils.isBlank(lectureDto.getName())) {
            detailMessage.add("강의 이름을 입력하십시오");
        }
        if (CommonUtils.isEmpty(lectureDto.getStatus()) || CommonUtils.isBlank(lectureDto.getStatus().name())) {
            detailMessage.add("강의 상태 코드를 입력하십시오");
        }
        if (LectureStatus.NULL.equals(lectureDto.getStatus())) {
            detailMessage.add("강의 상태 코드 값이 유효하지 않습니다");
        }
        if (CommonUtils.isBlank(lectureDto.getUseYn())) {
            detailMessage.add("강의의 사용여부를 입력하십시오");
        }
        if (!CommonUtils.hasYN(lectureDto.getUseYn())) {
            detailMessage.add("강의의 사용여부 값이 잘못되었습니다");
        }
        if (!CommonUtils.isEmpty(detailMessage.toString())) {
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, detailMessage.toString());
        }
        return CommonUtils.createProcessResult(ProcessCode.VALID, detailMessage.toString());
    }

    @Override
    public ProcessResultDto validateAfterPersist(LectureDto lectureDto) {
        String detailMessage = null;
        if (CommonUtils.isEmpty(lectureDto) || CommonUtils.isNotFoundData(lectureDto.getId())) {
            detailMessage = "저장 중 오류가 발생하였습니다.";
            return CommonUtils.createProcessResult(ProcessCode.FAIL, detailMessage);
        }
        return CommonUtils.createProcessResult(ProcessCode.SUCCESS, detailMessage);
    }

    public LectureOptionDto getOneByPrimaryIdAndOptionId(Long primaryId, Long optionId) {
        try {
            return lectureOptionRepository.findByIdAndLectureId(primaryId, optionId).orElseGet(this::emptyOption);
        } catch (Exception e) {
            return this.emptyOption();
        }
    }

    public LectureOptionDto write(LectureOptionDto lectureOptionDto) {
        try {
            return lectureOptionRepository.save(lectureOptionDto);
        } catch (Exception e) {
            return this.emptyOption();
        }
    }

    @Override
    public LectureDto empty() {
        return LectureDto.builder().id(GlobalConstants.NOT_FOUND_ID).build();
    }

    public LectureOptionDto emptyOption() {
        return LectureOptionDto.builder().id(GlobalConstants.NOT_FOUND_ID).build();
    }

    public ProcessResultDto validateAvailableDataByPrimaryIdAndOptionId(Long primaryId, Long optionId) {
        String detailMessage = null;
        if (CommonUtils.isEmpty(primaryId)) {
            detailMessage = "강의ID를 입력해주십시오.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, detailMessage);
        }
        if (CommonUtils.isEmpty(optionId)) {
            detailMessage = "강의옵션ID를 입력해주십시오.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, detailMessage);
        }
        if (!CommonUtils.isPositiveLong(primaryId)) {
            detailMessage = "강의ID는 1 이상의 정수여야 합니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, detailMessage);
        }
        if (!CommonUtils.isPositiveLong(optionId)) {
            detailMessage = "강의옵션ID는 1 이상의 정수여야 합니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, detailMessage);
        }

        // read data
        LectureOptionDto lectureOptionDto = this.getOneByPrimaryIdAndOptionId(primaryId, optionId);

        // validate if available data exists
        if (CommonUtils.isEmpty(lectureOptionDto.getLectureId())) {
            detailMessage = "존재하지 않는 강의입니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        if (CommonUtils.isNotFoundData(lectureOptionDto.getId()) || !CommonUtils.isUsable(lectureOptionDto.getUseYn())) {
            detailMessage = "존재하지 않는 강의옵션입니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        Date sysDate = DateUtils.getSysDate();
        int iCompareSysDateToLectureDateTime = DateUtils.compareTo(sysDate, lectureOptionDto.getLectureDatetime());
        if (iCompareSysDateToLectureDateTime>= 0) {
            detailMessage = "수업이 이미 시작된 강의입니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        if (iCompareSysDateToLectureDateTime == GlobalConstants.INT_WHEN_EXCEPTION) {
            detailMessage = "처리 중 오류가 발생하였습니다.";
            return CommonUtils.createProcessResult(ProcessCode.FAIL, detailMessage);
        }
        int iSysDateBetweenRegisterBeginAtAndRegisterEndAt = DateUtils.betweenFromTo(sysDate, lectureOptionDto.getRegisterBeginAt(), lectureOptionDto.getRegisterEndAt());
        if (iSysDateBetweenRegisterBeginAtAndRegisterEndAt == 0) {
            detailMessage = "수강 신청이 불가능한 강의입니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        if (iSysDateBetweenRegisterBeginAtAndRegisterEndAt == GlobalConstants.INT_WHEN_EXCEPTION) {
            detailMessage = "처리 중 오류가 발생하였습니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        if (lectureOptionDto.getCapacityLimit() == null) {
            detailMessage = "수강 정원에 대한 정보를 찾을 수 없습니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        long lUserLectureRegisterCount = userLectureUtils.getRegisterCount(lectureOptionDto.getLectureId(), lectureOptionDto.getId());
        if (lUserLectureRegisterCount <= lectureOptionDto.getCapacityLimit().longValue()) {
            detailMessage = "수강 신청 인원이 정원에 도달하였습니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        return CommonUtils.createProcessResult(ProcessCode.VALID, detailMessage);
    }

    public ProcessResultDto validateBeforePersistOnOption(LectureOptionDto lectureOptionDto) {
        // if arguments empty
        if (CommonUtils.isEmpty(lectureOptionDto)) {
            return CommonUtils.createProcessResult(ProcessCode.EMPTY, "입력할 데이터가 없습니다.");
        }
        // check available if positive id exists
        if (CommonUtils.isPositiveLong(lectureOptionDto.getId())) {
            LectureOptionDto existsLectureOptionDto = this.getOneByPrimaryIdAndOptionId(lectureOptionDto.getLectureId(), lectureOptionDto.getId());
            if (CommonUtils.isNotFoundData(existsLectureOptionDto.getId())) {
                return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, "존재하지 않는 강의옵션입니다.");
            }
        }

        StringJoiner detailMessage = new StringJoiner(",");
        // validate arguments in detail - null or empty
        if (CommonUtils.isEmpty(lectureOptionDto.getLectureId())) {
            detailMessage.add("강의ID를 입력해 주십시오");
        }
        if (CommonUtils.isEmpty(lectureOptionDto.getCapacityLimit())) {
            detailMessage.add("수강정원을 입력해 주십시오");
        }
        if (CommonUtils.isEmpty(lectureOptionDto.getRegisterBeginAt()) || CommonUtils.isEmpty(lectureOptionDto.getRegisterEndAt())) {
            detailMessage.add("수강신청 기간을 입력해 주십시오");
        }
        if (CommonUtils.isEmpty(lectureOptionDto.getLectureDatetime())) {
            detailMessage.add("강의일시를 입력해주십시오");
        }
        if (CommonUtils.isBlank(lectureOptionDto.getUseYn())) {
            detailMessage.add("강의옵션의 사용여부를 입력하십시오");
        }
        if (!CommonUtils.isEmpty(detailMessage.toString())) {
            return CommonUtils.createProcessResult(ProcessCode.EMPTY, detailMessage.toString());
        }

        // validate arguments in detail - not null but invalid arguments
        if (!CommonUtils.isEmpty(lectureOptionDto.getId()) && !CommonUtils.isPositiveLong(lectureOptionDto.getId())) {
            detailMessage.add("강의옵션ID를 확인해 주십시오");
        }
        if (!CommonUtils.isEmpty(lectureOptionDto.getLectureId()) && !CommonUtils.isPositiveLong(lectureOptionDto.getLectureId())) {
            detailMessage.add("강의ID를 확인해 주십시오");
        }
        if (!CommonUtils.isEmpty(lectureOptionDto.getCapacityLimit()) && !CommonUtils.isPositiveLong(lectureOptionDto.getCapacityLimit().longValue())) {
            detailMessage.add("수강정원을 확인해 주십시오");
        }
        if (DateUtils.compareTo(lectureOptionDto.getRegisterBeginAt(), lectureOptionDto.getRegisterEndAt()) > 0) {
            detailMessage.add("수강신청 기간을 확인해 주십시오");
        }
        Date sysDate = DateUtils.getSysDate();
        if (DateUtils.compareTo(sysDate, lectureOptionDto.getLectureDatetime()) >= 0) {
            detailMessage.add("강의일시는 현재일시보다 커야 합니다.");
        }
        if (!CommonUtils.hasYN(lectureOptionDto.getUseYn())) {
            detailMessage.add("강의옵션의 사용여부 값이 잘못되었습니다");
        }
        if (!CommonUtils.isEmpty(detailMessage.toString())) {
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, detailMessage.toString());
        }
        return CommonUtils.createProcessResult(ProcessCode.VALID, detailMessage.toString());
    }

    public ProcessResultDto validateAfterPersistOnOption(LectureOptionDto lectureOptionDto) {
        String detailMessage = null;
        if (CommonUtils.isEmpty(lectureOptionDto) || CommonUtils.isNotFoundData(lectureOptionDto.getId())) {
            detailMessage = "저장 중 오류가 발생하였습니다.";
            return CommonUtils.createProcessResult(ProcessCode.FAIL, detailMessage);
        }
        return CommonUtils.createProcessResult(ProcessCode.SUCCESS, detailMessage);
    }

    public LectureChangeHistoryDto setLectureChangeHistory(LectureDto lectureDto, LectureOptionDto lectureOptionDto) {
        return LectureChangeHistoryDto.builder()
                .lectureId(lectureDto.getId())
                .lectureName(lectureDto.getName())
                .lectureOptionId(lectureOptionDto.getId())
                .status(lectureDto.getStatus())
                .registerBeginAt(lectureOptionDto.getRegisterBeginAt())
                .registerEndAt(lectureOptionDto.getRegisterEndAt())
                .lectureDatetime(lectureOptionDto.getLectureDatetime())
                .capacityLimit(lectureOptionDto.getCapacityLimit())
                .build();
    }

    public LectureChangeHistoryDto emptyHistory() {
        return LectureChangeHistoryDto.builder()
                .lectureId(GlobalConstants.NOT_FOUND_ID)
                .build();
    }

    public LectureChangeHistoryDto write(LectureChangeHistoryDto lectureChangeHistoryDto) {
        try {
            return lectureChangeHistoryRepository.save(lectureChangeHistoryDto);
        } catch (Exception e) {
            return this.emptyHistory();
        }
    }
}
