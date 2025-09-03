package org.abraham.kanbantaskmanager.dtos;


public record UserEvent(
        int id,
        String username,
        String email
) {
}
