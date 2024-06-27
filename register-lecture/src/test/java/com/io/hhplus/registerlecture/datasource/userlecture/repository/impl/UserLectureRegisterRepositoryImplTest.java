package com.io.hhplus.registerlecture.datasource.userlecture.repository.impl;

import com.io.hhplus.registerlecture.business.userlecture.dto.UserLectureRegisterDto;
import com.io.hhplus.registerlecture.datasource.lecture.model.Lecture;
import com.io.hhplus.registerlecture.datasource.lecture.model.LectureOption;
import com.io.hhplus.registerlecture.datasource.lecture.repository.LectureJpaRepository;
import com.io.hhplus.registerlecture.datasource.lecture.repository.LectureOptionJpaRepository;
import com.io.hhplus.registerlecture.datasource.user.model.User;
import com.io.hhplus.registerlecture.datasource.user.repository.UserJpaRepository;
import com.io.hhplus.registerlecture.datasource.userlecture.mapper.UserLectureRegisterMapper;
import com.io.hhplus.registerlecture.datasource.userlecture.model.UserLectureRegister;
import com.io.hhplus.registerlecture.datasource.userlecture.repository.UserLectureRegisterJpaRepository;
import com.io.hhplus.registerlecture.global.utils.DateUtils;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserLectureRegisterRepositoryImplTest {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    LectureOptionJpaRepository lectureOptionJpaRepository;

    @Autowired
    LectureJpaRepository lectureJpaRepository;

    @Autowired
    UserLectureRegisterJpaRepository userLectureRegisterJpaRepository;

    private final UserLectureRegisterMapper userLectureRegisterMapper = new UserLectureRegisterMapper();

    /**
     * DB 확인 및 저장, 조회 시 쿼리, 값 정확하게 나오는지 확인
     * --------------------------------------------
     * 저장 시 리턴값에는 fk만 나오고 그 외 값들은 안 나옴
     * 조회해야 join 한 테이블의 다른 컬럼들이 세팅되어 노출
     */
    @Test
    void test_save_and_selectData_and_selectCount() {
        User user = User.builder()
                .id(null)
                .name("사용자")
                .useYn("Y")
                .build();
        User savedUser = userJpaRepository.save(user);

        Lecture lecture = Lecture.builder()
                .id(null)
                .name("과학")
                .useYn("Y")
                .status("OPEN")
                .build();
        Lecture savedLecture = lectureJpaRepository.save(lecture);

        LectureOption lectureOption = LectureOption.builder()
                .id(null)
                .lecture(savedLecture)
                .registerBeginAt(DateUtils.createTemporalDateByIntParameters(2024, 3, 24, 11, 11, 11))
                .registerEndAt(DateUtils.createTemporalDateByIntParameters(2024, 3, 27, 23, 59, 59))
                .lectureDatetime(DateUtils.createTemporalDateByIntParameters(2024, 4, 1, 13,0, 0))
                .capacityLimit(30)
                .useYn("Y")
                .build();
        LectureOption savedLectureOption = lectureOptionJpaRepository.save(lectureOption);

        UserLectureRegister userLectureRegister = UserLectureRegister.builder()
                .user(savedUser)
                .lecture(savedLecture)
                .lectureOption(savedLectureOption)
                .useYn("Y")
                .build();
        UserLectureRegister savedUserLectureRegister = userLectureRegisterJpaRepository.save(userLectureRegister);
        Optional<UserLectureRegister> selectUserLectureRegister = userLectureRegisterJpaRepository.findByUserIdAndLectureIdAndLectureOptionId(savedUser.getId(), savedLecture.getId(), savedLectureOption.getId());
        UserLectureRegisterDto savedUserLectureRegisterDtoDto = userLectureRegisterMapper.toDto(selectUserLectureRegister.get());
        long userLectureRegisterCount = userLectureRegisterJpaRepository.countByUserIdAndLectureIdAndLectureOptionIdAndUseYn(savedUser.getId(), savedLecture.getId(), savedLectureOption.getId(), "Y");
        long lectureRegisterCount = userLectureRegisterJpaRepository.countByLectureIdAndLectureOptionIdAndUseYn(savedLecture.getId(), savedLectureOption.getId(), "Y");
        assertEquals(savedUserLectureRegister.getId(), savedUserLectureRegisterDtoDto.getId());
        assertNotNull(savedUserLectureRegisterDtoDto.getLectureName());
        assertNotNull(savedUserLectureRegisterDtoDto.getUserName());
        assertEquals(1, userLectureRegisterCount);
        assertEquals(1, lectureRegisterCount);
    }
}