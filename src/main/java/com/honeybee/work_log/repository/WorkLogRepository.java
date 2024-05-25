package com.honeybee.work_log.repository;

import com.honeybee.work_log.domain.WorkLog;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
}
