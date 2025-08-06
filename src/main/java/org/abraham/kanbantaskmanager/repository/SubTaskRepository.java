package org.abraham.kanbantaskmanager.repository;

import org.abraham.kanbantaskmanager.entities.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {

    int countByTaskId(Long id);
}
