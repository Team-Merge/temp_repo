package com.project_merge.jigu_travel.api.user.service;

import com.project_merge.jigu_travel.api.user.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> findByLoginId(String loginId);
    String getLoginIdFromToken(String token);
    UUID getCurrentUserUUID();
}
