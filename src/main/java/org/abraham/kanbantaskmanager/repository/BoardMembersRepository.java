package org.abraham.kanbantaskmanager.repository;

import org.abraham.kanbantaskmanager.entities.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMembersRepository extends JpaRepository<BoardMember, Long> {
}
