package com.io.hhplus.registerlecture.datasource.lecture.repository;

import com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureOptionJpaRepository extends JpaRepository<LectureOption, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE) // 비관적 락 적용
    @Query("SELECT lo FROM LectureOption lo WHERE lo.lecture.id = :lectureId AND lo.id = :lectureOptionId")
    Optional<LectureOption> findByIdAndLectureIdWithPessimisticLock(@Param("lectureId") long lectureId, @Param("lectureOptionId") long lectureOptionId);
    List<LectureOption> findBydUseYn(String useYn);
}
