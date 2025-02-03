package com.project_merge.jigu_travel.api.user.repository;

import com.project_merge.jigu_travel.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByLoginIdAndDeletedFalse(String loginId);
    boolean existsByNickname(String nickname);
    boolean existsByLoginId(String loginId);
    Page<User> findAll(Pageable pageable);
}
