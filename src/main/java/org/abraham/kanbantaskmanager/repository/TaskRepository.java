package org.abraham.kanbantaskmanager.repository;

import org.abraham.kanbantaskmanager.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitleAndBoardId(String title, Long id);
}
