package com.io.hhplus.registerlecture.datasource.userlecture.model;

import com.io.hhplus.registerlecture.datasource.lecture.model.Lecture;
import com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption;
import com.io.hhplus.registerlecture.datasource.user.model.User;
import com.io.hhplus.registerlecture.global.audit.entity.AuditListener;
import com.io.hhplus.registerlecture.global.audit.entity.AuditSection;
import com.io.hhplus.registerlecture.global.audit.entity.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "user_lecture_register_table")
public class UserLectureRegister implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @OneToOne
    @JoinColumn(name = "lecture_option_id")
    private LectureOption lectureOption;

    @Column(name = "useYn", nullable = false, length = 1)
    private String useYn;

    @Builder.Default
    @Embedded
    private AuditSection auditSection = new AuditSection();
}
