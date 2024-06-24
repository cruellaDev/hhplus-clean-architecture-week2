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
@Table(name = "lecture_option_table")
public class LectureOption implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    Lecture lecture;

    @Column(name = "register_begin_at", nullable = false)
    private Date registerBeginAt;

    @Column(name = "register_end_at", nullable = false)
    private Date registerEndAt;

    @Column(name = "lecture_datetime", nullable = false)
    private Date lectureDatetime;

    @Column(name = "capacity_limit", nullable = false)
    private Integer capacityLimit;

    @Column(name = "useYn", nullable = false, length = 1)
    private String useYn;

    @Builder.Default
    @Embedded
    private AuditSection auditSection = new AuditSection();
}
