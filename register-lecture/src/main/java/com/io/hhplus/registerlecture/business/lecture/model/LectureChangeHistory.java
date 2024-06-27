package com.io.hhplus.registerlecture.business.lecture.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureChangeHistory {
    private Long id;
    private Long lectureId;
    private String lectureName;
    private Long lectureOptionId;
    private LectureStatus status;
    private Date registerBeginAt;
    private Date registerEndAt;
    private Date lectureDatetime;
    private Integer capacityLimit;
    private Date createdAt;
    private Date modifiedAt;
}
