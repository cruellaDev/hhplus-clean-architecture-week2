package com.io.hhplus.registerlecture.datasource.lecture.model;

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
@Table(name = "lecture_table")
public class Lecture implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "useYn", nullable = false, length = 1)
    private String useYn;

    @Builder.Default
    @Embedded
    private AuditSection auditSection = new AuditSection();
}
