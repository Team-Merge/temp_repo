package com.project_merge.jigu_travel.api.user.repository;

import com.project_merge.jigu_travel.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByLoginIdAndDeletedFalse(String loginId);
    boolean existsByNickname(String nickname);
    boolean existsByLoginId(String loginId);
    Optional<User> findByEmailAndDeletedFalse(String email); // 이메일로 사용자 찾기
}
