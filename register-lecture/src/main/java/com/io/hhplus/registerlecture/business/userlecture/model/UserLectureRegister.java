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
    private Long lectureId;
    private Long lectureOptionId;
    private String useYn;
    private Date createdAt;
    private Date modifiedAt;
}
