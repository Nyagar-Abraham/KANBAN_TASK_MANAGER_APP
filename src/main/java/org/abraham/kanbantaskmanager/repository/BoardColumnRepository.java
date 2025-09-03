package org.abraham.kanbantaskmanager.repository;

import org.abraham.kanbantaskmanager.entities.BoardColumn;
import org.abraham.kanbantaskmanager.entities.TaskStatusAndColumnName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {
    Optional<BoardColumn> findByName(TaskStatusAndColumnName name);

}
