package com.io.hhplus.registerlecture.business.userlecture.service;

import com.io.hhplus.registerlecture.business.lecture.dto.LectureOptionDto;
import com.io.hhplus.registerlecture.business.lecture.repository.LectureOptionRepository;
import com.io.hhplus.registerlecture.business.userlecture.dto.UserLectureRegisterDto;
import com.io.hhplus.registerlecture.business.userlecture.repository.UserLectureRegisterRepository;
import com.io.hhplus.registerlecture.business.utils.LectureUtils;
import com.io.hhplus.registerlecture.business.utils.UserLectureRegisterUtils;
import com.io.hhplus.registerlecture.business.utils.UserUtils;
import com.io.hhplus.registerlecture.global.processor.ProcessCode;
import com.io.hhplus.registerlecture.global.utils.CommonUtils;
import com.io.hhplus.registerlecture.global.utils.DateUtils;
import com.io.hhplus.registerlecture.presentation.model.ListResponse;
import com.io.hhplus.registerlecture.presentation.model.SingleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserLectureRegisterServiceTest {

    @InjectMocks
    UserLectureRegisterService userLectureRegisterService;

    @Mock
    UserUtils userUtils;

    @Mock
    LectureUtils lectureUtils;

    @Mock
    UserLectureRegisterUtils userLectureRegisterUtils;

    @Mock
    UserLectureRegisterRepository userLectureRegisterRepository;

    @Mock
    LectureOptionRepository lectureOptionRepository;

    private LectureOptionDto lectureOptionDto;

    @BeforeEach
    void setUp() {
        lectureOptionDto = LectureOptionDto.builder()
                .id(3L)
                .lectureId(2L)
                .lectureDatetime(DateUtils.createTemporalDateByIntParameters(2024, 8, 9, 13, 11, 19))
                .registerBeginAt(DateUtils.createTemporalDateByIntParameters(2024, 5, 16, 7, 8,9))
                .registerEndAt(DateUtils.createTemporalDateByIntParameters(2024, 10, 16, 7, 8,9))
                .capacityLimit(30)
                .useYn("Y")
                .build();
    }

    @Test
    void test_register_SUCCESS() {
        // given
        long userId = 1;
        long lectureId = 1;
        long lectureOptionId = 1;
        given(lectureOptionRepository.findByIdAndLectureId(anyLong(), anyLong())).willReturn(Optional.ofNullable(lectureOptionDto));
        given(userLectureRegisterRepository.save(any(UserLectureRegisterDto.class))).willReturn(UserLectureRegisterDto
                .builder().lectureId(lectureId).userId(userId).lectureOptionId(lectureOptionId).build());
        given(userUtils.validateAvailableDataByPrimaryId(anyLong())).willReturn(CommonUtils.createProcessResult(ProcessCode.SUCCESS, null));

        // when
        SingleResponse<UserLectureRegisterDto> response = userLectureRegisterService.register(userId, lectureId, lectureOptionId);

        // then
        assertNotNull(response);
        assertEquals(ProcessCode.SUCCESS.name(), response.getCode());
    }

    @Test
    void test_getAvailableLecture() {
        // given
        long lectureId = 2;
        long lectureOptionId = 8;
        List<LectureOptionDto> lectureOptionList = List.of(LectureOptionDto.builder().lectureId(lectureId).id(lectureOptionId).build());
        given(lectureOptionRepository.findAllAvailable(any(Date.class))).willReturn(lectureOptionList);
        given(lectureUtils.getAllAvailableLecture()).willReturn(lectureOptionList);
        // then
        ListResponse<LectureOptionDto> response = userLectureRegisterService.getAvailableLecture();

        // then
        assertNotNull(response);
        assertEquals(ProcessCode.SUCCESS.name(), response.getCode());
    }

    @Test
    void test_getRegisteredLecture() {
        long userId = 98;
        long lectureId1 = 34;
        long lectureId2 = 35;
        long lectureId3 = 36;
        long lectureOptionId1 = 78;
        long lectureOptionId2 = 99;
        long lectureOptionId3 = 105;

        List<UserLectureRegisterDto> userLectureRegisterList = List.of(
                UserLectureRegisterDto.builder()
                        .userId(userId)
                        .lectureId(lectureId1)
                        .lectureOptionId(lectureOptionId1)
                        .useYn("Y")
                        .build(),
                UserLectureRegisterDto.builder()
                        .userId(userId)
                        .lectureId(lectureId2)
                        .lectureOptionId(lectureOptionId2)
                        .useYn("Y")
                        .build(),
                UserLectureRegisterDto.builder()
                        .userId(userId)
                        .lectureId(lectureId3)
                        .lectureOptionId(lectureOptionId3)
                        .useYn("Y")
                        .build()
        );
        given(userLectureRegisterRepository.findAllByUserIdAndUseYn(anyLong(), anyString())).willReturn(userLectureRegisterList);
        given(userLectureRegisterUtils.getAllByUserId(userId)).willReturn(userLectureRegisterList);

        // when
        ListResponse<UserLectureRegisterDto> response = userLectureRegisterService.getRegisteredLecture(userId);
        // then
        assertNotNull(response);
        assertEquals(ProcessCode.SUCCESS.name(), response.getCode());
    }

}