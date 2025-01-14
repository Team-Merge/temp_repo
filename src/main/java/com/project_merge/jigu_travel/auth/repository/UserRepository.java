package com.project_merge.jigu_travel.auth.repository;

import com.project_merge.jigu_travel.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
