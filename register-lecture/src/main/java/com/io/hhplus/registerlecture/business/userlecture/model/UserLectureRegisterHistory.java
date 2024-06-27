package com.io.hhplus.registerlecture.business.userlecture.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLectureRegisterHistory {
    private Long id;
    private Long userLectureRegisterId;
    private Long userId;
    private Long lectureId;
    private Long lectureOptionId;
    private Date lectureDatetime;
    private UserLectureRegisterType type;
    private Date createdAt;
    private Date modifiedAt;
}
