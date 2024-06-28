package com.io.hhplus.registerlecture.presentation.userlecture.controller;

import com.io.hhplus.registerlecture.business.lecture.dto.LectureOptionDto;
import com.io.hhplus.registerlecture.business.userlecture.dto.UserLectureRegisterDto;
import com.io.hhplus.registerlecture.business.userlecture.service.UserLectureRegisterService;
import com.io.hhplus.registerlecture.presentation.model.ListResponse;
import com.io.hhplus.registerlecture.presentation.model.SingleResponse;
import com.io.hhplus.registerlecture.presentation.model.UserLectureRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/lectures")
@RestController
@RequiredArgsConstructor
public class UserLectureRegisterController {

    private final UserLectureRegisterService userLectureRegisterService;

    // 특강 신청
    @PostMapping("/apply")
    public SingleResponse<UserLectureRegisterDto> register(@RequestBody UserLectureRegisterRequest request) {
        return userLectureRegisterService.register(request.getUserId(), request.getLectureId(), request.getLectureOptionId());
    }

    // 특강 목록 조회
    @GetMapping("/")
    public ListResponse<LectureOptionDto> availableLectures() {
        return userLectureRegisterService.getAvailableLecture();
    }

    // 사용자별 신청 특강 목록 조회
    @GetMapping("/application/{userId}")
    public ListResponse<UserLectureRegisterDto> registeredLectures(@PathVariable("userId") Long userId) {
        return userLectureRegisterService.getRegisteredLecture(userId);
    }

}
