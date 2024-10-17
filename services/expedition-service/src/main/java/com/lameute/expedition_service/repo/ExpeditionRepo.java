package com.lameute.expedition_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpeditionRepo extends JpaRepository<Exception, Long> {
}
