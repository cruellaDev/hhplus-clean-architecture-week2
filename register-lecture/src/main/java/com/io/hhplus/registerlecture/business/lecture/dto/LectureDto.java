package com.io.hhplus.registerlecture.business.lecture.dto;

import com.io.hhplus.registerlecture.business.lecture.model.LectureStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureDto {
    private Long id;
    private String name;
    private LectureStatus status;
    private String useYn;
    private Date createdAt;
    private Date modifiedAt;
}
