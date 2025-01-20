package com.project_merge.jigu_travel.api.auth.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.project_merge.jigu_travel.api.auth.model.Auth;
import com.project_merge.jigu_travel.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Auth a SET a.accessToken = 'INVALIDATED' WHERE a.accessToken = :accessToken")
    void invalidateAccessToken(@Param("accessToken") String accessToken);

    @Modifying  // DELETE 쿼리 실행 시 필수
    @Transactional  // 트랜잭션 추가
    @Query("DELETE FROM Auth a WHERE a.user = :user")  // 명시적 JPQL 사용
    void deleteByUser(@Param("user") User user);
    Optional<Auth> findByAccessToken(String accessToken);

    // 사용자의 토큰 정보 조회
    Optional<Auth> findByUser(User user);
}
