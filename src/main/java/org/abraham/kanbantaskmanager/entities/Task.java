package org.abraham.kanbantaskmanager.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.abraham.kanbantaskmanager.dtos.TaskPriority;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Setter
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = true, name = "description")
    private String description;

    @Column(nullable = false,name = "status")
    private TaskStatusAndColumnName status;

    @Column(name = "position", insertable = false)
    private Integer position;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "created_at",insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(
            name = "assigned_to"
    )
    private User assignedTo;

    @ManyToOne
    @JoinColumn(
            name = "created_by"
    )
    private User createdBy;

    @Column(name = "tags")
    private List<String> tags;

    @Column(name = "estimated_hours")
    private Integer estimatedHours;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @ManyToOne
    @JoinColumn(
            name = "column_id"
    )
    private BoardColumn boardColumn;

    @OneToMany(mappedBy = "task",  cascade = {CascadeType.PERSIST,CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private Set<SubTask> subtasks = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "board_id"
    )
    private Board board;

    @OneToMany(mappedBy = "task")
    private List<TaskAttachments> attachments;

    @OneToMany(mappedBy = "task")
    private List<TaskComments> comments;

    public void addBoard(Board board) {
        this.board = board;
    }

    public void removeBoard(Board board) {
        this.board = board;
    }

    public void addSubTask(SubTask subTask) {
        this.subtasks.add(subTask);
        subTask.setTask(this);
    }

    public void removeSubTask(SubTask subTask) {
        this.subtasks.remove(subTask);
        subTask.setTask(null);
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null)
            createdAt = LocalDateTime.now();
    }


}
