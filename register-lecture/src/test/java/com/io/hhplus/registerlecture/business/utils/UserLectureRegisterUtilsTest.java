package com.io.hhplus.registerlecture.business.utils;

import com.io.hhplus.registerlecture.business.userlecture.dto.UserLectureRegisterDto;
import com.io.hhplus.registerlecture.business.userlecture.repository.UserLectureRegisterHistoryRepository;
import com.io.hhplus.registerlecture.business.userlecture.repository.UserLectureRegisterRepository;
import com.io.hhplus.registerlecture.global.processor.ProcessCode;
import com.io.hhplus.registerlecture.global.processor.ProcessResultDto;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserLectureRegisterUtilsTest {

    @InjectMocks
    private UserLectureRegisterUtils userLectureRegisterUtils;

    @Mock
    private UserLectureRegisterRepository userLectureRegisterRepository;

    @Mock
    private UserLectureRegisterHistoryRepository userLectureRegisterHistoryRepository;

    /**
     * LectureUtils 의 validateBeforePersist test - argument empty
     */
    @Test
    void test_validateBeforePersist_when_argument_is_empty() {
        // given
        UserLectureRegisterDto userLectureRegisterDto = null;
        // when
        ProcessResultDto processResultDto = userLectureRegisterUtils.validateBeforePersist(userLectureRegisterDto);
        // then
        assertEquals(ProcessCode.ILLEGAL_ARGUMENTS, processResultDto.getProcessCode());
        assertEquals("입력할 데이터가 없습니다.", processResultDto.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateBeforePersist test - userId empty
     */
    @Test
    void test_validateBeforePersist_when_userId_is_empty() {
        // given
        UserLectureRegisterDto userLectureRegisterDto = UserLectureRegisterDto.builder().userId(null).build();
        // when
        ProcessResultDto processResultDto = userLectureRegisterUtils.validateBeforePersist(userLectureRegisterDto);
        // then
        assertEquals(ProcessCode.ILLEGAL_ARGUMENTS, processResultDto.getProcessCode());
        assertEquals("사용자ID를 입력해 주십시오", processResultDto.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateBeforePersist test - lectureId empty
     */
    @Test
    void test_validateBeforePersist_when_lectureId_is_empty() {
        // given
        UserLectureRegisterDto userLectureRegisterDto = UserLectureRegisterDto.builder()
                .userId(1L)
                .lectureId(null)
                .build();
        // when
        ProcessResultDto processResultDto = userLectureRegisterUtils.validateBeforePersist(userLectureRegisterDto);
        // then
        assertEquals(ProcessCode.ILLEGAL_ARGUMENTS, processResultDto.getProcessCode());
        assertEquals("강의ID를 입력해 주십시오", processResultDto.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateBeforePersist test - lectureOptionId empty
     */
    @Test
    void test_validateBeforePersist_when_lectureOptionId_is_empty() {
        // given
        UserLectureRegisterDto userLectureRegisterDto = UserLectureRegisterDto.builder()
                .userId(1L)
                .lectureId(1L)
                .lectureOptionId(null)
                .build();
        // when
        ProcessResultDto processResultDto = userLectureRegisterUtils.validateBeforePersist(userLectureRegisterDto);
        // then
        assertEquals(ProcessCode.ILLEGAL_ARGUMENTS, processResultDto.getProcessCode());
        assertEquals("강의옵션ID를 입력해 주십시오", processResultDto.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateBeforePersist test - useYn empty
     */
    @Test
    void test_validateBeforePersist_when_useYn_is_empty() {
        // given
        UserLectureRegisterDto userLectureRegisterDto = UserLectureRegisterDto.builder()
                .userId(1L)
                .lectureId(1L)
                .lectureOptionId(1L)
                .useYn(null)
                .build();
        // when
        ProcessResultDto processResultDto = userLectureRegisterUtils.validateBeforePersist(userLectureRegisterDto);
        // then
        assertEquals(ProcessCode.ILLEGAL_ARGUMENTS, processResultDto.getProcessCode());
        assertEquals("신청여부를 입력해 주십시오", processResultDto.getDetailMessage());
    }

    /**
     * LectureUtils 의 validateBeforePersist test - useYn illegal
     */
    @Test
    void test_validateBeforePersist_when_useYn_is_illegal() {
        // given
        UserLectureRegisterDto userLectureRegisterDto = UserLectureRegisterDto.builder()
                .userId(1L)
                .lectureId(1L)
                .lectureOptionId(1L)
                .useYn("null")
                .build();
        // when
        ProcessResultDto processResultDto = userLectureRegisterUtils.validateBeforePersist(userLectureRegisterDto);
        // then
        assertEquals(ProcessCode.ILLEGAL_ARGUMENTS, processResultDto.getProcessCode());
        assertEquals("신청여부의 값을 확인해 주십시오", processResultDto.getDetailMessage());
    }

    /**
     * LectureUtils 의 getOneByUserIdAndLectureIdAndLectureOptionId test - 조회 테스트
     */
    @Test
    void test_getOneByUserIdAndLectureIdAndLectureOptionId() {
        // given
        long userId = 24;
        long lectureId = 45;
        long lectureOptionId = 109;
        UserLectureRegisterDto userLectureRegisterDto = UserLectureRegisterDto.builder()
                .userId(userId)
                .lectureId(lectureId)
                .lectureOptionId(lectureOptionId)
                .useYn("Y")
                .build();

        given(userLectureRegisterRepository.findByUserIdAndLectureIdAndLectureOptionId(anyLong(), anyLong(), anyLong())).willReturn(Optional.of(userLectureRegisterDto));

        // then
        UserLectureRegisterDto realUserLectureRegister = userLectureRegisterUtils.getOneByUserIdAndLectureIdAndLectureOptionId(userId, lectureId, lectureOptionId);

        assertEquals(userLectureRegisterDto.getUserId(), realUserLectureRegister.getUserId());
        assertEquals(userLectureRegisterDto.getLectureId(), realUserLectureRegister.getLectureId());
        assertEquals(userLectureRegisterDto.getLectureOptionId(), realUserLectureRegister.getLectureOptionId());

    }

    @Test
    void test_write_userLectureRegister() {
        // given
        long userId = 24;
        long lectureId = 45;
        long lectureOptionId = 109;
        UserLectureRegisterDto userLectureRegisterDto = UserLectureRegisterDto.builder()
                .userId(userId)
                .lectureId(lectureId)
                .lectureOptionId(lectureOptionId)
                .useYn("Y")
                .build();

        doReturn(userLectureRegisterDto).when(userLectureRegisterRepository).save(any(UserLectureRegisterDto.class));

        UserLectureRegisterDto realUserLectureRegister = userLectureRegisterUtils.write(userLectureRegisterDto);

        assertEquals(userLectureRegisterDto.getUserId(), realUserLectureRegister.getUserId());
        assertEquals(userLectureRegisterDto.getLectureId(), realUserLectureRegister.getLectureId());
        assertEquals(userLectureRegisterDto.getLectureOptionId(), realUserLectureRegister.getLectureOptionId());
    }

    /**
     * getUserLectureRegisterCount 개수 확인
     */
    @Test
    void test_getUserLectureRegisterCount() {
        // given
        long lectureId = 45;
        long lectureOptionId = 109;

        given(userLectureRegisterRepository.countByLectureIdAndLectureOptionIdAndUseYn(anyLong(), anyLong(), anyString())).willReturn(1L);

        long count = userLectureRegisterUtils.getUserLectureRegisterCount(lectureId, lectureOptionId);

        assertEquals(1, count);
    }

    /**
     * 이미 강의 신청했는지 확인
     */
    @Test
    void test_isAlreadyApplied() {
        // given
        long userId = 3;
        long lectureId = 88;
        long lectureOptionId = 182;

        given(userLectureRegisterRepository.countByUserIdAndLectureIdAndLectureOptionIdWithUseYn(anyLong(), anyLong(), anyLong(), anyString())).willReturn(1L);

        // when
        boolean isAlreadyApplied = userLectureRegisterUtils.isAlreadyApplied(userId, lectureId, lectureOptionId);

        // then
        assertTrue(isAlreadyApplied);
    }

    /**
     * userId로 신청내역 모두 가져오는지 확인
     */
    @Test
    void test_getAllByUserId() {
        // given
        long userId = 34;
        List<UserLectureRegisterDto> userLectureRegisterList = List.of(
                UserLectureRegisterDto.builder()
                    .userId(userId).lectureId(35L)
                    .lectureId(34L)
                    .lectureOptionId(234L)
                    .useYn("Y").build(),
                UserLectureRegisterDto.builder()
                        .userId(userId).lectureId(35L)
                        .lectureId(93L)
                        .lectureOptionId(2424L)
                        .useYn("Y").build()
                );

        given(userLectureRegisterRepository.findAllByUserIdAndUseYn(anyLong(), any())).willReturn(userLectureRegisterList);

        // when
        List<UserLectureRegisterDto> realUserLectureRegisterList = userLectureRegisterUtils.getAllByUserId(userId);
        assertEquals(2, realUserLectureRegisterList.size());
    }

}