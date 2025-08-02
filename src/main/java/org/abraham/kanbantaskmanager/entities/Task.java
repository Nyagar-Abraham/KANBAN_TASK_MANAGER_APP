package org.abraham.kanbantaskmanager.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
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
//    @Enumerated(EnumType.STRING)
    private TaskStatusAndColumnName status;

    @ManyToOne
    @JoinColumn(
            name = "column_id"
    )
    private BoardColumn boardColumn;

    @OneToMany(mappedBy = "task",  cascade = {CascadeType.PERSIST,CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private Set<SubTask> subTasks = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "board_id"
    )
    private Board board;

    public void addBoard(Board board) {
        this.board = board;
    }

    public void removeBoard(Board board) {
        this.board = board;
    }

    public void addSubTask(SubTask subTask) {
        this.subTasks.add(subTask);
        subTask.setTask(this);
    }

    public void removeSubTask(SubTask subTask) {
        this.subTasks.remove(subTask);
        subTask.setTask(null);
    }
}
