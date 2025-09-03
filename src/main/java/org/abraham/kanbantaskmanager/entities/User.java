package org.abraham.kanbantaskmanager.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "createdBy")
    private List<Board> board;

    @OneToMany(mappedBy = "createdBy")
    private List<Task> tasks;

    @ManyToMany(mappedBy = "members")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<TaskComments> comments;
}
