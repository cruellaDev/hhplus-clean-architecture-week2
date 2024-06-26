package com.io.hhplus.registerlecture.datasource.userlecture.model;

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
@Table(name = "user_lecture_register_history_table")
public class UserLectureRegisterHistory implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    /**
     * 로그성이기 때문에 join 걸지 않음.
     */
    @Column(name = "user_lecture_register_id", nullable = false)
    private Long userLectureRegisterId;

    /**
     * 로그성이기 때문에 join 걸지 않음.
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 로그성이기 때문에 join 걸지 않음.
     */
    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    /**
     * 로그성이기 때문에 join 걸지 않음.
     */
    @Column(name = "lecture_option_id", nullable = false)
    private Long lectureOptionId;

    /**
     * LectureOption 에서 변경될 수 있고 특강 신청 내역에서 가장 중요한 항목이기에 비정규화함
     */
    @Column(name = "lecture_datetime", nullable = false)
    private Date lectureDatetime;

    /**
     * 신청 / 취소 기록.
     */
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Builder.Default
    @Embedded
    private AuditSection auditSection = new AuditSection();
}
