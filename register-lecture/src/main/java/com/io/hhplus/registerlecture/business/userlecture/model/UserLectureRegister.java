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
public class UserLectureRegister {
    private Long id;
    private Long userId;
    private String userName;
    private Long lectureId;
    private String lectureName;
    private Long lectureOptionId;
    private Date registerBeginAt;
    private Date registerEndAt;
    private Date lectureDatetime;
    private Integer capacityLimit;
    private String useYn;
    private Date createdAt;
    private Date modifiedAt;
}
