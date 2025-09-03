package org.abraham.kanbantaskmanager.repository;

import org.abraham.kanbantaskmanager.entities.TaskComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCommentsRepository extends JpaRepository<TaskComments, Long> {
}
