package org.abraham.kanbantaskmanager.dtos;

import lombok.Data;
import org.abraham.kanbantaskmanager.entities.MemberRole;

import java.time.LocalDateTime;

@Data
public class BoardMemberResponse {
    private Long id;
    private MemberRole role;
    private LocalDateTime joinedAt;
    private Boolean isActive;
}
