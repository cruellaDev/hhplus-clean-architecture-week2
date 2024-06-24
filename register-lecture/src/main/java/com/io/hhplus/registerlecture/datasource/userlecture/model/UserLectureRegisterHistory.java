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

    @Column(name = "user_lecture_register_id", nullable = false)
    private Long userLectureRegisterId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @Column(name = "lecture_option_id", nullable = false)
    private Long lectureOptionId;

    @Column(name = "lecture_datetime", nullable = false)
    private Date lectureDatetime;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn;

    @Builder.Default
    @Embedded
    private AuditSection auditSection = new AuditSection();
}
