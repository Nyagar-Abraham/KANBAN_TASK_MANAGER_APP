package org.abraham.kanbantaskmanager.repository;

import org.abraham.kanbantaskmanager.entities.TaskAttachments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskAttachmentsRepository extends JpaRepository<TaskAttachments, Long> {
}
