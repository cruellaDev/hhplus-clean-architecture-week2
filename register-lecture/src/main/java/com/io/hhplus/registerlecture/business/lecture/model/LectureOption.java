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
public class LectureOption {
    private Long id;
    private Long lectureId;
    private Date registerBeginAt;
    private Date registerEndAt;
    private Integer capacityLimit;
    private Date lectureDatetime;
    private String useYn;
    private Date createdAt;
    private Date modifiedAt;
}
