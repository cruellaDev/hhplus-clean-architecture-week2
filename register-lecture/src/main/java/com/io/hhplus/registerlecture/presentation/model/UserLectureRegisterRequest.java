package com.io.hhplus.registerlecture.presentation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLectureRegisterRequest {
    private Long userId;
    private Long lectureId;
    private Long lectureOptionId;
}
