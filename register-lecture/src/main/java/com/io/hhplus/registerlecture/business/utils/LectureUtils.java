package com.io.hhplus.registerlecture.business.utils;

import com.io.hhplus.registerlecture.business.lecture.model.Lecture;
import com.io.hhplus.registerlecture.business.lecture.model.LectureOption;
import com.io.hhplus.registerlecture.business.lecture.model.LectureStatus;
import com.io.hhplus.registerlecture.business.lecture.model.LectureChangeHistory;
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
public class LectureUtils implements Reader<Lecture>, Processor<Lecture>, Writer<Lecture, Lecture> {

    private final LectureRepository lectureRepository;
    private final LectureOptionRepository lectureOptionRepository;
    private final LectureChangeHistoryRepository lectureChangeHistoryRepository;

    private final UserLectureUtils userLectureUtils;

    @Override
    public Lecture getOneByPrimaryId(Long primaryId) {
        try {
            return lectureRepository.findById(primaryId).orElseGet(this::empty);
        } catch (Exception e) {
            return this.empty();
        }
    }

    @Override
    public Lecture write(Lecture lecture) {
        try {
            return lectureRepository.save(lecture);
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
        Lecture lecture = this.getOneByPrimaryId(primaryId);

        // validate if available data exists
        if (CommonUtils.isNotFoundData(lecture.getId()) || !CommonUtils.isUsable(lecture.getUseYn())) {
            detailMessage = "존재하지 않는 강의입니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        if (!LectureStatus.OPEN.equals(lecture.getStatus())) {
            detailMessage = "해당 강의는 수강이 가능한 상태가 아닙니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }

        return CommonUtils.createProcessResult(ProcessCode.VALID, detailMessage);
    }

    @Override
    public ProcessResultDto validateBeforePersist(Lecture lecture) {
        // if arguments empty
        if (CommonUtils.isEmpty(lecture)) {
            return CommonUtils.createProcessResult(ProcessCode.EMPTY, "입력할 데이터가 없습니다.");
        }
        // check available if positive id exists
        if (CommonUtils.isPositiveLong(lecture.getId())) {
            Lecture existsLecture = this.getOneByPrimaryId(lecture.getId());
            if (CommonUtils.isNotFoundData(existsLecture.getId())) {
                return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, "존재하지 않는 강의입니다.");
            }
        }

        StringJoiner detailMessage = new StringJoiner(",");
        // validate arguments in detail
        if (!CommonUtils.isEmpty(lecture.getId()) && !CommonUtils.isPositiveLong(lecture.getId())) {
            detailMessage.add("강의ID를 확인해 주십시오");
        }
        if (CommonUtils.isBlank(lecture.getName())) {
            detailMessage.add("강의 이름을 입력하십시오");
        }
        if (CommonUtils.isEmpty(lecture.getStatus()) || CommonUtils.isBlank(lecture.getStatus().name())) {
            detailMessage.add("강의 상태 코드를 입력하십시오");
        }
        if (LectureStatus.NULL.equals(lecture.getStatus())) {
            detailMessage.add("강의 상태 코드 값이 유효하지 않습니다");
        }
        if (CommonUtils.isBlank(lecture.getUseYn())) {
            detailMessage.add("강의의 사용여부를 입력하십시오");
        }
        if (!CommonUtils.hasYN(lecture.getUseYn())) {
            detailMessage.add("강의의 사용여부 값이 잘못되었습니다");
        }
        if (!CommonUtils.isEmpty(detailMessage.toString())) {
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, detailMessage.toString());
        }
        return CommonUtils.createProcessResult(ProcessCode.VALID, detailMessage.toString());
    }

    @Override
    public ProcessResultDto validateAfterPersist(Lecture lecture) {
        String detailMessage = null;
        if (CommonUtils.isEmpty(lecture) || CommonUtils.isNotFoundData(lecture.getId())) {
            detailMessage = "저장 중 오류가 발생하였습니다.";
            return CommonUtils.createProcessResult(ProcessCode.FAIL, detailMessage);
        }
        return CommonUtils.createProcessResult(ProcessCode.SUCCESS, detailMessage);
    }

    public LectureOption getOneByPrimaryIdAndOptionId(Long primaryId, Long optionId) {
        try {
            return lectureOptionRepository.findByIdAndLectureId(primaryId, optionId).orElseGet(this::emptyOption);
        } catch (Exception e) {
            return this.emptyOption();
        }
    }

    public LectureOption write(LectureOption lectureOption) {
        try {
            return lectureOptionRepository.save(lectureOption);
        } catch (Exception e) {
            return this.emptyOption();
        }
    }

    @Override
    public Lecture empty() {
        return Lecture.builder().id(GlobalConstants.NOT_FOUND_ID).build();
    }

    public LectureOption emptyOption() {
        return LectureOption.builder().id(GlobalConstants.NOT_FOUND_ID).build();
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
        LectureOption lectureOption = this.getOneByPrimaryIdAndOptionId(primaryId, optionId);

        // validate if available data exists
        if (CommonUtils.isEmpty(lectureOption.getLectureId())) {
            detailMessage = "존재하지 않는 강의입니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        if (CommonUtils.isNotFoundData(lectureOption.getId()) || !CommonUtils.isUsable(lectureOption.getUseYn())) {
            detailMessage = "존재하지 않는 강의옵션입니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        Date sysDate = DateUtils.getSysDate();
        int iCompareSysDateToLectureDateTime = DateUtils.compareTo(sysDate, lectureOption.getLectureDatetime());
        if (iCompareSysDateToLectureDateTime>= 0) {
            detailMessage = "수업이 이미 시작된 강의입니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        if (iCompareSysDateToLectureDateTime == GlobalConstants.INT_WHEN_EXCEPTION) {
            detailMessage = "처리 중 오류가 발생하였습니다.";
            return CommonUtils.createProcessResult(ProcessCode.FAIL, detailMessage);
        }
        int iSysDateBetweenRegisterBeginAtAndRegisterEndAt = DateUtils.betweenFromTo(sysDate, lectureOption.getRegisterBeginAt(), lectureOption.getRegisterEndAt());
        if (iSysDateBetweenRegisterBeginAtAndRegisterEndAt == 0) {
            detailMessage = "수강 신청이 불가능한 강의입니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        if (iSysDateBetweenRegisterBeginAtAndRegisterEndAt == GlobalConstants.INT_WHEN_EXCEPTION) {
            detailMessage = "처리 중 오류가 발생하였습니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        if (lectureOption.getCapacityLimit() == null) {
            detailMessage = "수강 정원에 대한 정보를 찾을 수 없습니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        long lUserLectureRegisterCount = userLectureUtils.getRegisterCount(lectureOption.getLectureId(), lectureOption.getId());
        if (lUserLectureRegisterCount <= lectureOption.getCapacityLimit().longValue()) {
            detailMessage = "수강 신청 인원이 정원에 도달하였습니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }
        return CommonUtils.createProcessResult(ProcessCode.VALID, detailMessage);
    }

    public ProcessResultDto validateBeforePersistOnOption(LectureOption lectureOption) {
        // if arguments empty
        if (CommonUtils.isEmpty(lectureOption)) {
            return CommonUtils.createProcessResult(ProcessCode.EMPTY, "입력할 데이터가 없습니다.");
        }
        // check available if positive id exists
        if (CommonUtils.isPositiveLong(lectureOption.getId())) {
            LectureOption existsLectureOption = this.getOneByPrimaryIdAndOptionId(lectureOption.getLectureId(), lectureOption.getId());
            if (CommonUtils.isNotFoundData(existsLectureOption.getId())) {
                return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, "존재하지 않는 강의옵션입니다.");
            }
        }

        StringJoiner detailMessage = new StringJoiner(",");
        // validate arguments in detail - null or empty
        if (CommonUtils.isEmpty(lectureOption.getLectureId())) {
            detailMessage.add("강의ID를 입력해 주십시오");
        }
        if (CommonUtils.isEmpty(lectureOption.getCapacityLimit())) {
            detailMessage.add("수강정원을 입력해 주십시오");
        }
        if (CommonUtils.isEmpty(lectureOption.getRegisterBeginAt()) || CommonUtils.isEmpty(lectureOption.getRegisterEndAt())) {
            detailMessage.add("수강신청 기간을 입력해 주십시오");
        }
        if (CommonUtils.isEmpty(lectureOption.getLectureDatetime())) {
            detailMessage.add("강의일시를 입력해주십시오");
        }
        if (CommonUtils.isBlank(lectureOption.getUseYn())) {
            detailMessage.add("강의옵션의 사용여부를 입력하십시오");
        }
        if (!CommonUtils.isEmpty(detailMessage.toString())) {
            return CommonUtils.createProcessResult(ProcessCode.EMPTY, detailMessage.toString());
        }

        // validate arguments in detail - not null but invalid arguments
        if (!CommonUtils.isEmpty(lectureOption.getId()) && !CommonUtils.isPositiveLong(lectureOption.getId())) {
            detailMessage.add("강의옵션ID를 확인해 주십시오");
        }
        if (!CommonUtils.isEmpty(lectureOption.getLectureId()) && !CommonUtils.isPositiveLong(lectureOption.getLectureId())) {
            detailMessage.add("강의ID를 확인해 주십시오");
        }
        if (!CommonUtils.isEmpty(lectureOption.getCapacityLimit()) && !CommonUtils.isPositiveLong(lectureOption.getCapacityLimit().longValue())) {
            detailMessage.add("수강정원을 확인해 주십시오");
        }
        if (DateUtils.compareTo(lectureOption.getRegisterBeginAt(), lectureOption.getRegisterEndAt()) > 0) {
            detailMessage.add("수강신청 기간을 확인해 주십시오");
        }
        Date sysDate = DateUtils.getSysDate();
        if (DateUtils.compareTo(sysDate, lectureOption.getLectureDatetime()) >= 0) {
            detailMessage.add("강의일시는 현재일시보다 커야 합니다.");
        }
        if (!CommonUtils.hasYN(lectureOption.getUseYn())) {
            detailMessage.add("강의옵션의 사용여부 값이 잘못되었습니다");
        }
        if (!CommonUtils.isEmpty(detailMessage.toString())) {
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, detailMessage.toString());
        }
        return CommonUtils.createProcessResult(ProcessCode.VALID, detailMessage.toString());
    }

    public ProcessResultDto validateAfterPersistOnOption(LectureOption lectureOption) {
        String detailMessage = null;
        if (CommonUtils.isEmpty(lectureOption) || CommonUtils.isNotFoundData(lectureOption.getId())) {
            detailMessage = "저장 중 오류가 발생하였습니다.";
            return CommonUtils.createProcessResult(ProcessCode.FAIL, detailMessage);
        }
        return CommonUtils.createProcessResult(ProcessCode.SUCCESS, detailMessage);
    }

    public LectureChangeHistory setLectureChangeHistory(Lecture lecture, LectureOption lectureOption) {
        return LectureChangeHistory.builder()
                .lectureId(lecture.getId())
                .lectureName(lecture.getName())
                .lectureOptionId(lectureOption.getId())
                .status(lecture.getStatus())
                .registerBeginAt(lectureOption.getRegisterBeginAt())
                .registerEndAt(lectureOption.getRegisterEndAt())
                .lectureDatetime(lectureOption.getLectureDatetime())
                .capacityLimit(lectureOption.getCapacityLimit())
                .build();
    }

    public LectureChangeHistory emptyHistory() {
        return LectureChangeHistory.builder()
                .lectureId(GlobalConstants.NOT_FOUND_ID)
                .build();
    }

    public LectureChangeHistory write(LectureChangeHistory lectureChangeHistory) {
        try {
            return lectureChangeHistoryRepository.save(lectureChangeHistory);
        } catch (Exception e) {
            return this.emptyHistory();
        }
    }
}
