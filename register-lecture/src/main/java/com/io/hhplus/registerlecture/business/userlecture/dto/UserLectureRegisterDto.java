package com.io.hhplus.registerlecture.business.userlecture.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLectureRegisterDto {
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
    private Integer currentCapacity;
    private String useYn;
    private Date createdAt;
    private Date modifiedAt;
}
