package com.io.hhplus.registerlecture.datasource.user.repository.impl;

import com.io.hhplus.registerlecture.datasource.user.model.User;
import com.io.hhplus.registerlecture.datasource.user.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DB 연결 및 JPA 테스트
 */
@ActiveProfiles("test")
@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryImplTest {

    @Autowired
    UserJpaRepository userJpaRepository;

    /**
     * User 테이블에 데이터 없을 시 빈 Optional 객체 return.
     */
    @Test
    void findById_when_data_is_empty_then_return_empty() {
        // given
        long id = 1L;
        // when
        Optional<User> user = userJpaRepository.findById(id);
        // then
        assertTrue(user.isEmpty());
    }

    /**
     * User 테이블 신규 데이터 생성 확인
     */
    @Test
    void save_user() {
        // given
        User user = User.builder()
                .id(null)
                .name("사용자")
                .useYn("Y")
                .build();

        // when
        // auditSection 추가 후 다시 테스트 함
        User savedUser = userJpaRepository.save(user);
        // then
        assertEquals(user.getUseYn(), savedUser.getUseYn());
        assertEquals(user.getName(), savedUser.getName());
    }

    /**
     * 사용자 목록 모두 저장 후 모두 조회
     */
    @Test
    void save_all_user_and_find_all_user() {
        // given
        List<User> userList = List.of(
                User.builder()
                    .id(null)
                    .name("사용자1")
                    .useYn("Y")
                    .build(),
                User.builder()
                        .id(null)
                        .name("사용자2")
                        .useYn("Y")
                        .build()
        );
        // when
        userJpaRepository.saveAll(userList);
        List<User> selectedUserList = userJpaRepository.findAll();
        // then
        assertEquals(2, selectedUserList.size());
    }

}