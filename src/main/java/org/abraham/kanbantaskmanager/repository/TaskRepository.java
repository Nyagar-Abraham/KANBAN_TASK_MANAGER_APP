package org.abraham.kanbantaskmanager.repository;

import org.abraham.kanbantaskmanager.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitleAndBoardId(String title, Long id);


    List<Task> findByBoardIdAndBoardColumnId(Long boardId, Long columnId);

    int countByBoardColumnId(Long columnId);
}
