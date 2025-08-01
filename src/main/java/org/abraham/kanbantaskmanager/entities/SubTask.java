package org.abraham.kanbantaskmanager.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subtasks")
@Setter
@Getter
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "is_completed")
    private Boolean isCompleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
