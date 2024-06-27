package com.io.hhplus.registerlecture.business.lecture.repository;

import com.io.hhplus.registerlecture.business.lecture.model.Lecture;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectureRepository {
    Optional<Lecture> findById(long lectureId);
    Lecture save(Lecture lecture);
}
