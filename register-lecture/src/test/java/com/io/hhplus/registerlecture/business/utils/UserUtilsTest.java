package com.io.hhplus.registerlecture.business.utils;

import com.io.hhplus.registerlecture.business.user.model.User;
import com.io.hhplus.registerlecture.business.user.repository.UserRepository;
import com.io.hhplus.registerlecture.global.processor.ProcessCode;
import com.io.hhplus.registerlecture.global.processor.ProcessResultDto;
import com.io.hhplus.registerlecture.global.utils.CommonUtils;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserUtilsTest {

    @InjectMocks
    private UserUtils userUtils;

    @Mock
    private UserRepository userRepository;

    /**
     * UserUtils 의 getOneByPrimaryId test : 데이터가 아무 것도 없으면 id가 -1인 user 리턴
     */
    @Test
    void test_getOneByPrimaryId_when_data_is_empty() {
        // given
        long id = 3;
        User expectedUser = userUtils.empty();
        given(userRepository.findById(anyLong())).willReturn(Optional.of(expectedUser));
        // when
        User realUser = userUtils.getOneByPrimaryId(id);
        // then
        assertEquals(-1, realUser.getId());
    }

    /**
     * UserUtils 의 validateBeforePersist test : 사용자 저장 전 데이터 유효성 체크 - null 을 인자로 받았을 경우
     */
    @Test
    void meets_validateBeforePersist_when_user_is_null_then_return_EMPTY() {
        // given
        User user = null;
        ProcessResultDto expectedProcessResult = CommonUtils.createProcessResult(ProcessCode.EMPTY, "입력할 데이터가 없습니다.");
        // when
        ProcessResultDto realProcessResult = userUtils.validateBeforePersist(user);
        // then
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult.getDetailMessage());
    }

    /**
     * UserUtils 의 validateBeforePersist test : 사용자 저장 전 데이터 유효성 체크 - 사용자ID가 음수일 경우
     */
    @Test
    void meets_validateBeforePersist_when_id_is_negative() {
        // given
        User user = User.builder()
                .id(-4L)
                .useYn("Y")
                .build();
        // when
        ProcessResultDto expectedProcessResult = CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "사용자의 ID를 확인해 주십시오,사용자의 이름을 입력하십시오");
        ProcessResultDto realProcessResult = userUtils.validateBeforePersist(user);
        // then
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult.getDetailMessage());
    }

    /**
     * UserUtils 의 validateBeforePersist test : 사용자 저장 전 데이터 유효성 체크 - 사용자명이 null이거나 빈값일 경우
     */
    @Test
    void meets_validateBeforePersist_when_userName_is_null_or_blank_then_return_ILLEGAL_ARGUMENTS() {
        // given
        User user1 = User.builder()
                .id(null)
                .useYn("Y")
                .build();
        User user2 = User.builder()
                .id(null)
                .name("")
                .useYn("Y")
                .build();

        ProcessResultDto expectedProcessResult = CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "사용자의 이름을 입력하십시오");
        // when
        ProcessResultDto realProcessResult1 = userUtils.validateBeforePersist(user1);
        ProcessResultDto realProcessResult2 = userUtils.validateBeforePersist(user2);

        // then
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult1.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult1.getDetailMessage());
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult2.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult2.getDetailMessage());
    }

    /**
     * UserUtils 의 validateBeforePersist test : 사용자 저장 전 데이터 유효성 체크 - 사용자 데이터 유효할 시 VALID 반환
     */
    @Test
    void meets_validateBeforePersist_when_data_is_valid_then_return_VALID() {
        // given
        User user1 = User.builder()
                .id(null)
                .name("사용자명")
                .useYn("Y")
                .build();

        ProcessResultDto expectedProcessResult = CommonUtils.createProcessResult(ProcessCode.VALID, null);
        // when
        ProcessResultDto realProcessResult1 = userUtils.validateBeforePersist(user1);
        System.out.println(realProcessResult1);
        // then
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult1.getProcessCode());
    }

    /**
     * UserUtils 의 validateBeforePersist test : 사용자 저장 전 데이터 유효성 체크 - 사용여부가 null이거나 빈값일 경우
     */
    @Test
    void meets_validateBeforePersist_when_useYn_is_null_or_blank_then_return_ILLEGAL_ARGUMENTS() {
        // given
        User user1 = User.builder()
                .id(null)
                .name("사용자명")
                .build();
        User user2 = User.builder()
                .id(null)
                .name("사용자명1")
                .useYn("")
                .build();

        // 오류 메시지 모두 보여주는 것으로 로직 변경하여 테스트 코드도 변경
//        ProcessResultDto expectedProcessResult = CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "사용자의 사용여부를 입력하십시오");
        ProcessResultDto expectedProcessResult = CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "사용자의 사용여부를 입력하십시오,사용자의 사용여부 값이 잘못되었습니다.");
        // when
        ProcessResultDto realProcessResult1 = userUtils.validateBeforePersist(user1);
        ProcessResultDto realProcessResult2 = userUtils.validateBeforePersist(user2);

        // then
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult1.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult1.getDetailMessage());
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult2.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult2.getDetailMessage());
    }

    /**
     * UserUtils 의 validateBeforePersist test : 사용자 저장 전 데이터 유효성 체크 - 사용여부가 Y 나 N 이 아닐 경우
     */
    @Test
    void meets_validateBeforePersist_when_useYn_is_not_Y_neither_N_then_return_ILLEGAL_ARGUMENTS() {
        // given
        User user1 = User.builder()
                .id(null)
                .name("사용자명")
                .useYn("가나다라")
                .build();
        User user2 = User.builder()
                .id(null)
                .name("사용자명1")
                .useYn("NefeNNN")
                .build();

        ProcessResultDto expectedProcessResult = CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, "사용자의 사용여부 값이 잘못되었습니다.");
        // when
        ProcessResultDto realProcessResult1 = userUtils.validateBeforePersist(user1);
        ProcessResultDto realProcessResult2 = userUtils.validateBeforePersist(user2);

        // then
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult1.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult1.getDetailMessage());
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult2.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult2.getDetailMessage());
    }

    /**
     * UserUtils 의 validateBeforePersist test : 사용자 저장 전 데이터 유효성 체크 - detail 메시지가 여러 개일 경우 합쳐서 보여줌
     */
    @Test
    void meets_validateBeforePersist_when_has_multiple_detailMessage_return_all() {
        // given
        User user1 = User.builder()
                .id(-1L)
                .name("")
                .useYn("")
                .build();

        ProcessResultDto expectedProcessResult = CommonUtils.createProcessResult(
                ProcessCode.ILLEGAL_ARGUMENTS,
                "사용자의 ID를 확인해 주십시오,사용자의 이름을 입력하십시오,사용자의 사용여부를 입력하십시오,사용자의 사용여부 값이 잘못되었습니다."
        );
        // when
        ProcessResultDto realProcessResult = userUtils.validateBeforePersist(user1);
        // then
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult.getDetailMessage());
    }

    /**
     * UserUtils 의 validateBeforePersist test : 사용자 저장 전 데이터 유효성 체크 - userId 있을 시 수정이므로 데이터 조회해서 user 있는지 확인, 없으면 ILLEGAL_STATE 반환
     */
    @Test
    void meets_validateBeforePersist_when_userId_exists_and_user_not_exists_then_return_ILLEGAL_STATE() {
        // given
        User user1 = User.builder()
                .id(9999L)
                .name("사용자명")
                .useYn("Y")
                .build();

        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        ProcessResultDto expectedProcessResult = CommonUtils.createProcessResult(
                ProcessCode.ILLEGAL_STATE,
                "존재하지 않는 사용자 입니다."
        );
        // when
        ProcessResultDto realProcessResult = userUtils.validateBeforePersist(user1);
        // then
        assertEquals(expectedProcessResult.getProcessCode(), realProcessResult.getProcessCode());
        assertEquals(expectedProcessResult.getDetailMessage(), realProcessResult.getDetailMessage());
    }

    /**
     * UserUtils 의 write test - 데이터 저장 확인
     */
    @Test
    void test_write() {
        // given
        User user = User.builder()
                .id(1L)
                .name("사용자1")
                .useYn("Y")
                .build();
        doReturn(user).when(userRepository).save(any(User.class));
        // when
        User savedUser = userUtils.write(user);
        // then
        assertEquals(user.getId(), savedUser.getId());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getUseYn(), savedUser.getUseYn());
        assertNotNull(savedUser.getId());
    }

    /**
     * UserUtils 의 write test - 데이터 저장 성공 시 SUCCESS 반환
     */
    @Test
    void test_write_when_succeeded_then_processResultCode_SUCCESS() {
        // given
        User user = User.builder()
                .id(1L)
                .name("사용자1")
                .useYn("Y")
                .build();
        doReturn(user).when(userRepository).save(any(User.class));
        // when
        User savedUser = userUtils.write(user);
        ProcessResultDto processResultDto = userUtils.validateAfterPersist(savedUser);
        // then
        assertEquals(ProcessCode.SUCCESS, processResultDto.getProcessCode());
    }

    /**
     * UserUtils 의 validateAvailableDataByPrimaryId test - argument 인 userId 가 null 일 경우 ILLEGAL_ARGUMENTS 리턴 확인
     */
    @Test
    void meets_validateAvailableDataByPrimaryId_when_argument_is_null_then_return_ILLEGAL_ARGUMENTS() {
        // given
        Long id = null;
        // when
        ProcessResultDto processResultDto = userUtils.validateAvailableDataByPrimaryId(id);
        // then
        assertEquals(ProcessCode.ILLEGAL_ARGUMENTS, processResultDto.getProcessCode());
        assertEquals("사용자ID를 입력해주십시오.", processResultDto.getDetailMessage());
    }

    /**
     * UserUtils 의 validateAvailableDataByPrimaryId test - argument 인 userId 가 음수일 경우 ILLEGAL_ARGUMENTS 리턴 확인
     */
    @Test
    void meets_validateAvailableDataByPrimaryId_when_argument_is_less_than_zero_then_return_ILLEGAL_ARGUMENTS() {
        // given
        long id = -3;
        // when
        ProcessResultDto processResultDto = userUtils.validateAvailableDataByPrimaryId(id);
        // then
        assertEquals(ProcessCode.ILLEGAL_ARGUMENTS, processResultDto.getProcessCode());
        assertEquals("사용자ID는 1 이상의 정수여야 합니다.", processResultDto.getDetailMessage());
    }

    /**
     * UserUtils 의 validateAvailableDataByPrimaryId test - 조회되는 사용자의 정보가 유효하지 않을 경우 ILLEGAL_STATE 리턴 확인
     */
    @Test
    void meets_validateAvailableDataByPrimaryId_when_user_is_not_available_then_return_ILLEGAL_STATE() {
        long id = 1;
        User user = User.builder()
                .id(id)
                .name("사용자1")
                .useYn("N")
                .build();
        doReturn(user).when(userRepository).save(any(User.class));
        // when
        User savedUser = userUtils.write(user);
        ProcessResultDto processResultDto = userUtils.validateAvailableDataByPrimaryId(savedUser.getId());
        // then
        assertEquals(ProcessCode.ILLEGAL_STATE, processResultDto.getProcessCode());
        assertEquals("존재하지 않는 사용자입니다.", processResultDto.getDetailMessage());
    }

    /**
     * UserUtils 의 validateAvailableDataByPrimaryId test - 조회되는 사용자가 없을 경우 ILLEGAL_STATE 리턴 확인
     */
    @Test
    void meets_validateAvailableDataByPrimaryId_when_user_is_not_found_then_return_ILLEGAL_STATE() {
        // given
        Optional<User> optionalUser = Optional.of(User.builder().id(-1L).build());
        long id = 99999;

        given(userRepository.findById(anyLong())).willReturn(optionalUser);

        // when
        ProcessResultDto processResultDto = userUtils.validateAvailableDataByPrimaryId(id);
        // then
        assertEquals(ProcessCode.ILLEGAL_STATE, processResultDto.getProcessCode());
        assertEquals("존재하지 않는 사용자입니다.", processResultDto.getDetailMessage());
    }

    /**
     * UserUtils 의 validateAvailableDataByPrimaryId test - 조회되는 사용자가 유효할 경우 VALID 리턴 확인
     */
    @Test
    void meets_validateAvailableDataByPrimaryId_when_data_is_available_then_return_VALID() {
        long id = 1;
        User user = User.builder()
                .id(id)
                .name("사용자1")
                .useYn("Y")
                .build();
        doReturn(user).when(userRepository).save(any(User.class));
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        // when
        userUtils.write(user);
        ProcessResultDto processResultDto = userUtils.validateAvailableDataByPrimaryId(id);
        // then
        assertEquals(ProcessCode.VALID, processResultDto.getProcessCode());
        assertNull(processResultDto.getDetailMessage());
    }
}