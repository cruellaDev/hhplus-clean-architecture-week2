package com.io.hhplus.registerlecture.business.utils;

import com.io.hhplus.registerlecture.business.lecture.dto.LectureDto;
import com.io.hhplus.registerlecture.business.lecture.dto.LectureOptionDto;
import com.io.hhplus.registerlecture.business.lecture.model.LectureStatus;
import com.io.hhplus.registerlecture.business.lecture.repository.LectureOptionRepository;
import com.io.hhplus.registerlecture.business.lecture.repository.LectureRepository;
import com.io.hhplus.registerlecture.business.userlecture.repository.UserLectureRegisterRepository;
import com.io.hhplus.registerlecture.global.processor.ProcessCode;
import com.io.hhplus.registerlecture.global.processor.ProcessResultDto;
import com.io.hhplus.registerlecture.global.utils.CommonUtils;
import com.io.hhplus.registerlecture.global.utils.DateUtils;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LectureUtilsTest {

    @InjectMocks
    private LectureUtils lectureUtils;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private LectureOptionRepository lectureOptionRepository;

    @Mock
    private UserLectureRegisterRepository userLectureRegisterRepository;

    /**
     * LectureUtils 의 getOneByPrimaryId test - 데이터가 아무 것도 없으면 id가 -1인 기본 값 리턴
     */
    @Test
    void test_getOneByPrimaryId_when_data_is_empty() {
        // given
        long id = 9;
        LectureDto expectedLectureDto = lectureUtils.empty();
        given(lectureRepository.findById(anyLong())).willReturn(Optional.of(expectedLectureDto));
        // when
        LectureDto realLectureDto = lectureUtils.getOneByPrimaryId(id);
        // then
        assertEquals(-1, realLectureDto.getId());
    }

    /**
     * LectureUtils 의 validateBeforePersist test : 강의 저장 전 데이터 유효성 체크 - null 을 인자로 받았을 경우
     */
    @Test
    void meets_validateBeforePersist_when_lecture_is_null_then_return_EMPTY() {
        // given
        LectureDto lectureDto = null;
        ProcessResultDto expectedProcessResult = CommonUtils.createProcessResult(ProcessCode.EMPTY, "입력할 데이터가 없습니다.");
        // when
        ProcessResultDto realProcessResult = lectureUtils.validateBeforePersist(lectureDto);
        // then
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateBeforePersist test : 강의 저장 전 데이터 유효성 체크 - 강의ID가 음수일 경우
     */
    @Test
    void meets_validateBeforePersist_when_id_is_negative() {
        // given
        long id = -99;
        LectureDto lectureDto = LectureDto.builder()
                .id(id)
                .build();
        // when
        ProcessResultDto expectedProcessResult = CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "강의ID를 확인해 주십시오");
        // then
        ProcessResultDto realProcessResult = lectureUtils.validateAfterPersist(lectureDto);
    }

    /**
     * LectureUtils 의 validateBeforePersist test : 강의 저장 전 데이터 유효성 체크 - 강의명이 null이거나 빈 값일 경우
     */
    @Test
    void meets_validBeforePersist_when_lectureName_is_null_or_blank() {
        // given
        LectureDto lectureDto1 = LectureDto.builder()
                .id(null)
                .name(null)
                .status(LectureStatus.OPEN)
                .useYn("Y")
                .build();
        LectureDto lectureDto2 = LectureDto.builder()
                .id(null)
                .name("")
                .status(LectureStatus.OPEN)
                .useYn("Y")
                .build();
        ProcessResultDto expectedProcessResult = CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "강의 이름을 입력하십시오");
        // when
        ProcessResultDto realProcessResult1 = lectureUtils.validateBeforePersist(lectureDto1);
        ProcessResultDto realProcessResult2 = lectureUtils.validateBeforePersist(lectureDto2);
        // then
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult1.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult1.getDetailMessage());
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult2.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult2.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateBeforePersist test : 강의 저장 전 데이터 유효성 체크 - 강의상태가 null 이거나 LectureStatus.NULL일 경우
     */
    @Test
    void meets_validateBeforePersist_when_lecture_status_is_null_or_blank() {
        // given
        LectureDto lectureDto1 = LectureDto.builder()
                .id(null)
                .name("강의명1")
                .status(null)
                .useYn("Y")
                .build();
        LectureDto lectureDto2 = LectureDto.builder()
                .id(null)
                .name("강의명2")
                .status(LectureStatus.NULL)
                .useYn("Y")
                .build();
        ProcessResultDto expectedProcessResult1 = CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "강의 상태 코드를 입력하십시오");
        ProcessResultDto expectedProcessResult2 = CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "강의 상태 코드 값이 유효하지 않습니다");
        // when
        ProcessResultDto realProcessResult1 = lectureUtils.validateBeforePersist(lectureDto1);
        ProcessResultDto realProcessResult2 = lectureUtils.validateBeforePersist(lectureDto2);
        // then
        assertEquals(expectedProcessResult1.getProcessCode(), realProcessResult1.getProcessCode());
        assertEquals(expectedProcessResult1.getDetailMessage(), realProcessResult1.getDetailMessage());
        assertEquals(expectedProcessResult2.getProcessCode(), realProcessResult2.getProcessCode());
        assertEquals(expectedProcessResult2.getDetailMessage(), realProcessResult2.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateBeforePersist test : 강의 저장 전 데이터 유효성 체크 - 시용여부가 null 이거나 빈값일 경우
     */
    @Test
    void meets_validateBeforePersist_when_lecture_useYn_is_null_or_blank() {
        // given
        LectureDto lectureDto1 = LectureDto.builder()
                .id(null)
                .name("강의명1")
                .status(LectureStatus.OPEN)
                .useYn(null)
                .build();
        LectureDto lectureDto2 = LectureDto.builder()
                .id(null)
                .name("강의명2")
                .status(LectureStatus.OPEN)
                .useYn("")
                .build();
        ProcessResultDto expectedProcessResult1 = CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "강의의 사용여부를 입력하십시오,강의의 사용여부 값이 잘못되었습니다");
        ProcessResultDto expectedProcessResult2 = CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "강의의 사용여부를 입력하십시오,강의의 사용여부 값이 잘못되었습니다");
        // when
        ProcessResultDto realProcessResult1 = lectureUtils.validateBeforePersist(lectureDto1);
        ProcessResultDto realProcessResult2 = lectureUtils.validateBeforePersist(lectureDto2);
        // then
        assertEquals(expectedProcessResult1.getProcessCode(), realProcessResult1.getProcessCode());
        assertEquals(expectedProcessResult1.getDetailMessage(), realProcessResult1.getDetailMessage());
        assertEquals(expectedProcessResult2.getProcessCode(), realProcessResult2.getProcessCode());
        assertEquals(expectedProcessResult2.getDetailMessage(), realProcessResult2.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateBeforePersist test : 강의 저장 전 데이터 유효성 체크 - 사용여부가 Y 나 N 이 아닐 경우
     */
    @Test
    void meets_validateBeforePersist_when_useYn_is_not_Y_neither_N_then_return_ILLEGAL_ARGUMENTS() {
        // given
        LectureDto lectureDto = LectureDto.builder()
                .id(null)
                .name("강의명1")
                .status(LectureStatus.OPEN)
                .useYn("2389dsfdf")
                .build();
        ProcessResultDto expectedProcessResult = CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "강의의 사용여부 값이 잘못되었습니다");
        // when
        ProcessResultDto realProcessResult = lectureUtils.validateBeforePersist(lectureDto);
        // then
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateBeforePersist test : 강의 저장 전 데이터 유효성 체크 - lectureId 있을 시 수정이므로 데이터 조회해서 lecture 있는지 확인, 없으면 ILLEGAL_STATE 반환
     */
    @Test
    void meets_validateBeforePersist_when_lectureId_exists_and_lecture_not_exists_then_return_ILLEGAL_STATE() {
        // given
        LectureDto lectureDto = LectureDto.builder()
                .id(9999L)
                .build();
        given(lectureRepository.findById(anyLong())).willReturn(Optional.empty());
        ProcessResultDto expectedProcessResult = CommonUtils.createProcessResult(
                ProcessCode.ILLEGAL_STATE,
                "존재하지 않는 강의입니다."
        );
        // when
        ProcessResultDto realProcessResult = lectureUtils.validateBeforePersist(lectureDto);
        // then
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult.getDetailMessage());
    }

    /**
     * LectureUtils 의 write test - 데이터 저장 확인
     */
    @Test
    void test_write() {
        // given
        LectureDto lectureDto = LectureDto.builder()
                .id(1L)
                .name("강의명")
                .status(LectureStatus.OPEN)
                .useYn("Y")
                .build();
        doReturn(lectureDto).when(lectureRepository).save(any(LectureDto.class));
        // when
        LectureDto savedLectureDto = lectureUtils.write(lectureDto);
        // then
        assertEquals(lectureDto.getId(), savedLectureDto.getId());
        assertEquals(lectureDto.getName(), savedLectureDto.getName());
        assertEquals(lectureDto.getStatus(), savedLectureDto.getStatus());
        assertEquals(lectureDto.getUseYn(), savedLectureDto.getUseYn());
    }

    /**
     * LectureUtils 의 validateAvailableDataByPrimaryId test - primaryId인 lectureId 가 null 일 경우
     */
    @Test
    void meets_validateAvailableDataByPrimaryId_when_argument_is_null_then_return_ILLEGAL_ARGUMENTS() {
        // given
        Long id = null;
        // when
        ProcessResultDto processResultDto = lectureUtils.validateAvailableDataByPrimaryId(id);
        // then
        assertEquals(ProcessCode.ILLEGAL_ARGUMENTS, processResultDto.getProcessCode());
        assertEquals("강의ID를 입력해주십시오.", processResultDto.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateAvailableDataByPrimaryId test - argument 인 lectureId 가 음수일 경우
     */
    @Test
    void meets_validateAvailableDataByPrimaryId_when_argument_is_less_than_zero_then_return_ILLEGAL_ARGUMENTS() {
        // given
        long id = -3;
        // when
        ProcessResultDto processResultDto = lectureUtils.validateAvailableDataByPrimaryId(id);
        // then
        assertEquals(ProcessCode.ILLEGAL_ARGUMENTS, processResultDto.getProcessCode());
        assertEquals("강의ID는 1 이상의 정수여야 합니다.", processResultDto.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateAvailableDataByPrimaryId test - 조회되는 강의의 정보를 사용할 수 없는 경우
     */
    @Test
    void meets_validateAvailableDataByPrimaryId_when_lecture_is_not_usable_then_return_ILLEGAL_STATE() {
        long id = 1;
        LectureDto lectureDto = LectureDto.builder()
                .id(id)
                .name("강의명1")
                .status(LectureStatus.OPEN)
                .useYn("N")
                .build();
        doReturn(lectureDto).when(lectureRepository).save(any(LectureDto.class));
        given(lectureRepository.findById(id)).willReturn(Optional.of(lectureDto));
        // when
        LectureDto savedLectureDto = lectureUtils.write(lectureDto);
        ProcessResultDto processResultDto = lectureUtils.validateAvailableDataByPrimaryId(savedLectureDto.getId());
        // then
        assertEquals(ProcessCode.ILLEGAL_STATE, processResultDto.getProcessCode());
        assertEquals("존재하지 않는 강의입니다.", processResultDto.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateAvailableDataByPrimaryId test - 조회되는 강의가 없을 경우 ILLEGAL_STATE 리턴 확인
     */
    @Test
    void meets_validateAvailableDataByPrimaryId_when_lecture_is_not_found_then_return_ILLEGAL_STATE() {
        // given
        Optional<LectureDto> optionalLecture = Optional.of(lectureUtils.empty());
        long id = 9999;

        given(lectureRepository.findById(anyLong())).willReturn(optionalLecture);

        ProcessResultDto processResultDto = lectureUtils.validateAvailableDataByPrimaryId(id);
        // then
        assertEquals(ProcessCode.ILLEGAL_STATE, processResultDto.getProcessCode());
        assertEquals("존재하지 않는 강의입니다.", processResultDto.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateAvailableDataByPrimaryId test - 조회한 강의상태가 OPEN이 아닌 경우 ILLEGAL_STATE 리턴 확인
     */
    @Test
    void meets_validateAvailableDataByPrimaryId_when_lectureStatus_is_not_OPEN_then_return_ILLEGAL_STATE() {
        long id = 1;
        LectureDto lectureDto = LectureDto.builder()
                .id(id)
                .name("강의명1")
                .status(LectureStatus.CLOSED)
                .useYn("Y")
                .build();
        doReturn(lectureDto).when(lectureRepository).save(any(LectureDto.class));
        given(lectureRepository.findById(id)).willReturn(Optional.of(lectureDto));
        // when
        LectureDto savedLectureDto = lectureUtils.write(lectureDto);
        ProcessResultDto processResultDto = lectureUtils.validateAvailableDataByPrimaryId(savedLectureDto.getId());
        // then
        assertEquals(ProcessCode.ILLEGAL_STATE, processResultDto.getProcessCode());
        assertEquals("해당 강의는 수강이 가능한 상태가 아닙니다.", processResultDto.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateAvailableDataByPrimaryIdAndOptionId test - 강의 날짜가 현재일시보다 이전 날짜
     */
    @Test
    void meets_validateAvailableDataByPrimaryIdAndOptionId_when_lecture_already_began() {
        // given
        long lectureId = 1;
        long lectureOptionId = 1;
        Date lectureDatetime = DateUtils.createTemporalDateByIntParameters(2024, 3, 17, 14, 0, 0);
        Date registerBeginAt = DateUtils.createTemporalDateByIntParameters(2024, 2, 17, 14, 0, 0);
        Date registerEndAt = DateUtils.createTemporalDateByIntParameters(2025, 3, 17, 14, 0, 0);
        LectureOptionDto lectureOptionDto = LectureOptionDto.builder()
                .id(lectureOptionId)
                .lectureId(lectureId)
                .lectureDatetime(lectureDatetime)
                .capacityLimit(30)
                .registerBeginAt(registerBeginAt)
                .registerEndAt(registerEndAt)
                .build();
        doReturn(lectureOptionDto).when(lectureOptionRepository).save(any(LectureOptionDto.class));
        given(lectureOptionRepository.findByIdAndLectureId(anyLong(), anyLong())).willReturn(Optional.of(lectureOptionDto));
        // when
        LectureOptionDto savedLectureOptionDto = lectureUtils.write(lectureOptionDto);
        ProcessResultDto processResultDto = lectureUtils.validateAvailableDataByPrimaryIdAndOptionId(savedLectureOptionDto.getLectureId(), savedLectureOptionDto.getId());
        // then
        assertEquals(ProcessCode.ILLEGAL_STATE, processResultDto.getProcessCode());
        assertEquals("수업이 이미 시작된 강의입니다.", processResultDto.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateAvailableDataByPrimaryIdAndOptionId test - 수강신청기간 이탈
     */
    @Test
    void meets_validateAvailableDataByPrimaryIdAndOptionId_when_lecture_register_duration_out_of_range() {
        // given
        long lectureId = 1;
        long lectureOptionId = 1;
        Date lectureDatetime = DateUtils.createTemporalDateByIntParameters(2024, 8, 17, 14, 0, 0);
        Date registerBeginAt = DateUtils.createTemporalDateByIntParameters(2024, 2, 17, 14, 0, 0);
        Date registerEndAt = DateUtils.createTemporalDateByIntParameters(2024, 3, 17, 14, 0, 0);
        LectureOptionDto lectureOptionDto = LectureOptionDto.builder()
                .id(lectureOptionId)
                .lectureId(lectureId)
                .lectureDatetime(lectureDatetime)
                .capacityLimit(30)
                .registerBeginAt(registerBeginAt)
                .registerEndAt(registerEndAt)
                .build();
        doReturn(lectureOptionDto).when(lectureOptionRepository).save(any(LectureOptionDto.class));
        given(lectureOptionRepository.findByIdAndLectureId(anyLong(), anyLong())).willReturn(Optional.of(lectureOptionDto));
        // when
        LectureOptionDto savedLectureOptionDto = lectureUtils.write(lectureOptionDto);
        ProcessResultDto processResultDto = lectureUtils.validateAvailableDataByPrimaryIdAndOptionId(savedLectureOptionDto.getLectureId(), savedLectureOptionDto.getId());
        // then
        assertEquals(ProcessCode.ILLEGAL_STATE, processResultDto.getProcessCode());
        assertEquals("수강 신청이 불가능한 강의입니다.", processResultDto.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateAvailableDataByPrimaryIdAndOptionId test - 수강 정원 초과
     */
    @Test
    void meets_validateAvailableDataByPrimaryIdAndOptionId_when_lecture_register_capacity_out_of_limit() {
        // given
        long lectureId = 1;
        long lectureOptionId = 1;
        Date lectureDatetime = DateUtils.createTemporalDateByIntParameters(2024, 8, 17, 14, 0, 0);
        Date registerBeginAt = DateUtils.createTemporalDateByIntParameters(2024, 2, 17, 14, 0, 0);
        Date registerEndAt = DateUtils.createTemporalDateByIntParameters(2024, 9, 17, 14, 0, 0);
        Integer capacityLimit = 1;
        LectureOptionDto lectureOptionDto = LectureOptionDto.builder()
                .id(lectureOptionId)
                .lectureId(lectureId)
                .lectureDatetime(lectureDatetime)
                .capacityLimit(capacityLimit)
                .registerBeginAt(registerBeginAt)
                .registerEndAt(registerEndAt)
                .build();
        doReturn(lectureOptionDto).when(lectureOptionRepository).save(any(LectureOptionDto.class));
        given(lectureOptionRepository.findByIdAndLectureId(anyLong(), anyLong())).willReturn(Optional.of(lectureOptionDto));
        given(userLectureRegisterRepository.countByLectureIdAndLectureOptionIdAndUseYn(anyLong(), anyLong(), anyString())).willReturn(capacityLimit.longValue());
        // when
        LectureOptionDto savedLectureOptionDto = lectureUtils.write(lectureOptionDto);
        ProcessResultDto processResultDto = lectureUtils.validateAvailableDataByPrimaryIdAndOptionId(savedLectureOptionDto.getLectureId(), savedLectureOptionDto.getId());
        // then
        assertEquals(ProcessCode.ILLEGAL_STATE, processResultDto.getProcessCode());
        assertEquals("수강 신청 인원이 정원에 도달하였습니다.", processResultDto.getDetailMessage());
    }
}