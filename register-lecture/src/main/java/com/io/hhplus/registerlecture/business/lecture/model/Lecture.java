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
public class Lecture {
    private Long id;
    private String name;
    private String status;
    private String useYn;
    private Date createdAt;
    private Date modifiedAt;
}
