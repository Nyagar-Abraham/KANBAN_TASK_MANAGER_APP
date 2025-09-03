package org.abraham.kanbantaskmanager.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_comments")
public class TaskComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at",insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(
            name = "task_id"
    )
    private Task task;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;
}
