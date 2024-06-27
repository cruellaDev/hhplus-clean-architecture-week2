package com.io.hhplus.registerlecture.datasource.lecture.model;

import com.io.hhplus.registerlecture.global.audit.entity.AuditListener;
import com.io.hhplus.registerlecture.global.audit.entity.AuditSection;
import com.io.hhplus.registerlecture.global.audit.entity.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "lecture_change_history_table")
public class LectureChangeHistory implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    /**
     * 로그성이기 때문에 join 걸지 않음.
     */
    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    /**
     * 변경될 수 있는 부분이라 비정규화
     */
    @Column(name = "lecture_name", nullable = false, length = 100)
    private String lectureName;

    /**
     * 로그성이기 때문에 join 걸지 않음.
     */
    @Column(name = "lecture_option_id", nullable = false)
    private Long lectureOptionId;

    /**
     * 변경될 수 있는 부분이라 비정규화
     */
    @Column(name = "status", nullable = false, length = 50)
    private String status;

    /**
     * 변경될 수 있는 부분이라 비정규화
     */
    @Column(name = "register_begin_at", nullable = false)
    private Date registerBeginAt;

    /**
     * 변경될 수 있는 부분이라 비정규화
     */
    @Column(name = "register_end_at", nullable = false)
    private Date registerEndAt;

    /**
     * 변경될 수 있는 부분이라 비정규화
     */
    @Column(name = "lecture_datetime", nullable = false)
    private Date lectureDatetime;

    /**
     * 변경될 수 있는 부분이라 비정규화
     */
    @Column(name = "capacity_limit", nullable = false)
    private Integer capacityLimit;

    @Builder.Default
    @Embedded
    private AuditSection auditSection = new AuditSection();
}
