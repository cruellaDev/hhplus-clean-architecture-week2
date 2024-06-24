package com.io.hhplus.registerlecture.global.audit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 공통 audit entity 구현
 * @link <a href="https://stackoverflow.com/questions/221611/creation-timestamp-and-last-update-timestamp-with-hibernate-and-mysql">Using JPA @EntityListeners</a>
 */
@Getter
@Setter
@Embeddable
public class AuditSection {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at", nullable = false)
    private Date modifiedAt;
}