package com.io.hhplus.registerlecture.datasource.lecture.repository;

import com.io.hhplus.registerlecture.datasource.lecture.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
}
