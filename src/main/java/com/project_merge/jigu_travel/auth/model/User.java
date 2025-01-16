package com.project_merge.jigu_travel.auth.model;

import com.project_merge.jigu_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID userId;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Location location;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isAdmin = false;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;

    public enum Location {
        KOR, USA, JPN
    }

}
