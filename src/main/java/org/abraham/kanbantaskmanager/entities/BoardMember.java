package org.abraham.kanbantaskmanager.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "board_members")
@Getter
@Setter
public class BoardMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(name = "joined_at",insertable = false, updatable = false)
    private LocalDateTime joinedAt;

    @Column(name = "is_active")
    private Boolean isActive;
}
