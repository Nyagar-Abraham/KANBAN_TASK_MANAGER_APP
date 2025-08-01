package org.abraham.kanbantaskmanager.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "boards")
@Setter
@Getter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "name",unique = true )
    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "board_column_join",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "column_id")
    )

    private Set<BoardColumn> columns = new HashSet<>();

    public void addColumn(BoardColumn column) {
        columns.add(column);

    }

    public void removeColumn(BoardColumn column) {
        columns.remove(column);
    }


}
