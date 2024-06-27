package com.io.hhplus.registerlecture.business.utils;

import com.io.hhplus.registerlecture.business.user.dto.UserDto;
import com.io.hhplus.registerlecture.business.user.repository.UserRepository;
import com.io.hhplus.registerlecture.global.processor.ProcessCode;
import com.io.hhplus.registerlecture.global.processor.ProcessResultDto;
import com.io.hhplus.registerlecture.global.processor.Processor;
import com.io.hhplus.registerlecture.global.reader.Reader;
import com.io.hhplus.registerlecture.global.utils.CommonUtils;
import com.io.hhplus.registerlecture.global.utils.GlobalConstants;
import com.io.hhplus.registerlecture.global.writer.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
@RequiredArgsConstructor
public class UserUtils implements Reader<UserDto>, Processor<UserDto>, Writer<UserDto, UserDto> {

    private final UserRepository userRepository;

    @Override
    public ProcessResultDto validateAvailableDataByPrimaryId(Long primaryId) {
        String detailMessage = null;

        // validate arguments
        if (CommonUtils.isEmpty(primaryId)) {
            detailMessage = "사용자ID를 입력해주십시오.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, detailMessage);
        }
        if (!CommonUtils.isPositiveLong(primaryId)) {
            detailMessage = "사용자ID는 1 이상의 정수여야 합니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, detailMessage);
        }

        // read data
        UserDto userDto = this.getOneByPrimaryId(primaryId);

        // validate if available data exists
        if (CommonUtils.isNotFoundData(userDto.getId()) || !CommonUtils.isUsable(userDto.getUseYn())) {
            detailMessage = "존재하지 않는 사용자입니다.";
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, detailMessage);
        }

        return CommonUtils.createProcessResult(ProcessCode.VALID, detailMessage);
    }

    @Override
    public ProcessResultDto validateBeforePersist(UserDto userDto) {
        // if arguments empty
        if (CommonUtils.isEmpty(userDto)) {
            return CommonUtils.createProcessResult(ProcessCode.EMPTY, "입력할 데이터가 없습니다.");
        }

        // check available if positive id exists
         if (CommonUtils.isPositiveLong(userDto.getId())) {
             UserDto existsUserDto = this.getOneByPrimaryId(userDto.getId());
             if (CommonUtils.isNotFoundData(existsUserDto.getId())) {
                 return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_STATE, "존재하지 않는 사용자 입니다.");
             }
         }

        StringJoiner detailMessage = new StringJoiner(",");
        // validate arguments in detail
         if (!CommonUtils.isEmpty(userDto.getId()) && !CommonUtils.isPositiveLong(userDto.getId())) {
             detailMessage.add("사용자의 ID를 확인해 주십시오");
         }
        if (CommonUtils.isBlank(userDto.getName())) {
            detailMessage.add("사용자의 이름을 입력하십시오");
        }
        if (CommonUtils.isBlank(userDto.getUseYn())) {
            detailMessage.add("사용자의 사용여부를 입력하십시오");
        }
        if (!CommonUtils.hasYN(userDto.getUseYn())) {
            detailMessage.add("사용자의 사용여부 값이 잘못되었습니다.");
        }
        if (!CommonUtils.isEmpty(detailMessage.toString())) {
            return CommonUtils.createProcessResult(ProcessCode.ILLEGAL_ARGUMENTS, detailMessage.toString());
        }

        return CommonUtils.createProcessResult(ProcessCode.VALID, detailMessage.toString());
    }

    @Override
    public ProcessResultDto validateAfterPersist(UserDto userDto) {
        String detailMessage = null;
        if (CommonUtils.isEmpty(userDto) || CommonUtils.isNotFoundData(userDto.getId())) {
            detailMessage = "저장 중 오류가 발생하였습니다.";
            return CommonUtils.createProcessResult(ProcessCode.FAIL, detailMessage);
        }
        return CommonUtils.createProcessResult(ProcessCode.SUCCESS, detailMessage);
    }

    @Override
    public UserDto getOneByPrimaryId(Long primaryId) {
        try {
            return userRepository.findById(primaryId).orElseGet(this::empty);
        } catch (Exception e) {
            return this.empty();
        }
    }

    @Override
    public UserDto write(UserDto userDto) {
        try {
            return userRepository.save(userDto);
        } catch (Exception e) {
            return this.empty();
        }
    }

    @Override
    public UserDto empty() {
        return UserDto.builder().id(GlobalConstants.NOT_FOUND_ID).build();
    }
}
