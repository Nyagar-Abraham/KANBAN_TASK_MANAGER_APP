package org.abraham.kanbantaskmanager.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "board_columns")
@Setter
@Getter
public class BoardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name",unique = true)
    @Enumerated(EnumType.STRING)
    private TaskStatusAndColumnName name;

    @Column(name = "position",insertable = false)
    private Integer position;

    @Column(name = "color")
    private String color;

    @Column(name = "task_limit")
    private Integer taskLimit;

    @Column(name = "created_at",insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "boardColumn", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Task> tasks = new HashSet<>();

    public void addTask(Task task) {
        tasks.add(task);
        task.setBoardColumn(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setBoardColumn(null);
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null)
            createdAt = LocalDateTime.now();
    }
}
